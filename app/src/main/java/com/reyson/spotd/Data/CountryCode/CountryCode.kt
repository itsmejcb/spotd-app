package com.reyson.spotd.Data.CountryCode

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.reyson.spotd.Data.CountryCode.Model.FirstResponse
import com.reyson.spotd.Data.CountryCode.Model.SecondResponse
import com.reyson.spotd.Repository.RetroInstances
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountryCodeViewModel : ViewModel() {
    private val TAG = "CountryCodeViewModel"

    // LiveData for holding the second API response
    val countryDetailsLiveData: MutableLiveData<String?> = MutableLiveData()

    fun fetchCountryDetails() {
        val firstApiService = RetroInstances.getRetroInstance().create(FirstCodeApi::class.java)

        // Make the first API call to get the IP
        firstApiService.getCountryIP()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCountryIPObserver())
    }

    private fun getCountryIPObserver(): Observer<FirstResponse> {
        return object : Observer<FirstResponse> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "First API Call onSubscribe")
            }

            override fun onNext(response: FirstResponse) {
                // Once we have the IP from the first API call, use it to make the second call
                val secondApiService = RetroInstances.getRetroInstance2().create(SecondCodeApi::class.java)

                // Pass the dynamically obtained IP to the second API call
                response.ip?.let {
                    secondApiService.getCountryDetails(it)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getCountryDetailsObserver())
                    Log.i("ip",it)
                }
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "First API Call Error: ${e.message}")
                countryDetailsLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d(TAG, "First API Call Completed")
            }
        }
    }

    private fun getCountryDetailsObserver(): Observer<SecondResponse> {
        return object : Observer<SecondResponse> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "Second API Call onSubscribe")
            }

            override fun onNext(response: SecondResponse) {
                // Handle the response from the second API call here
                val countryDetails = "${response.countryCode}, ${response.regionCode}, ${response.cityCode}"
                countryDetailsLiveData.postValue(response.countryCode)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "Second API Call Error: ${e.message}")
                countryDetailsLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d(TAG, "Second API Call Completed")
            }
        }
    }
}
