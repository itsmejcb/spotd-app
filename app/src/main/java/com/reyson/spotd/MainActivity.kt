package com.reyson.spotd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.reyson.spotd.Data.CountryCode.CountryCodeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var countryCodeViewModel: CountryCodeViewModel
    private var textViewCountryCode: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewCountryCode = findViewById<TextView>(R.id.textViewCountryCode)
        // Initialize ViewModel
        countryCodeViewModel = ViewModelProvider(this).get(CountryCodeViewModel::class.java)

        // Start fetching country details
        countryCodeViewModel.fetchCountryDetails()

        // Observe LiveData changes
        countryCodeViewModel.countryDetailsLiveData.observe(this, Observer { countryCode ->
            countryCode?.let {
                updateUIWithCountryCode(countryCode)
            } ?: run {
                handleNullCountryCode()
            }
        })
    }

    private fun updateUIWithCountryCode(countryCode: String) {
        textViewCountryCode?.text = countryCode
    }

    private fun handleNullCountryCode() {
        textViewCountryCode?.text = "Failed to fetch country code"
    }
}
