package com.reyson.spotd.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.reyson.spotd.Class.Activity.SignIn.Model.Request
import com.reyson.spotd.Class.Activity.SignIn.Model.Response
import com.reyson.spotd.Data.Api.Access.Companion.loadTOKEN
import com.reyson.spotd.Repository.RetroInstance
import com.reyson.spotd.Repository.UserApi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ViewModel : ViewModel() {
    var loginLiveData: MutableLiveData<Response?> = MutableLiveData()
    var createEmailLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response?> =
        MutableLiveData()
    var createFullNameLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response?> =
        MutableLiveData()
    var createUserNameLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response?> =
        MutableLiveData()
    var createProfilePictureLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response?> =
        MutableLiveData()
    var createPostLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response?> =
        MutableLiveData()
    var fetchForyouLiveData: MutableLiveData<com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response?> =
        MutableLiveData()
    var fetchNotificationLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.Notification.Model.Response?> =
        MutableLiveData()
    var fetchProfileLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.Profile.Model.Response?> =
        MutableLiveData()
    var fetchProfilePostLiveData: MutableLiveData<com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response?> =
        MutableLiveData()
    var fetchPostLiveData: MutableLiveData<com.reyson.spotd.Class.Activity.Posts.Image.Model.Response?> =
        MutableLiveData()

    fun getLoginObserver(): MutableLiveData<Response?> {
        return loginLiveData
    }

    fun getCreateEmailObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response?> {
        return createEmailLiveData
    }

    fun getCreateFullNameObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response?> {
        return createFullNameLiveData
    }

    fun getCreateUsernameObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response?> {
        return createUserNameLiveData
    }

    fun getCreateProfilePictureObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response?> {
        return createProfilePictureLiveData
    }

    fun getCreatePostObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response?> {
        return createPostLiveData
    }
    fun getFetchForyouObserver(): MutableLiveData<com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response?> {
        return fetchForyouLiveData
    }
    fun getFetchNotificationObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.Notification.Model.Response?> {
        return fetchNotificationLiveData
    }
    fun getFetchProfileObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.Profile.Model.Response?> {
        return fetchProfileLiveData
    }
    fun getFetchProfilePostObserver(): MutableLiveData<com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response?> {
        return fetchProfilePostLiveData
    }
    fun getFetchPostObserver(): MutableLiveData<com.reyson.spotd.Class.Activity.Posts.Image.Model.Response?> {
        return fetchPostLiveData
    }

    @SuppressLint("CheckResult")
    fun userLogin(userRequest: Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.signInto(
            userRequest.email,
            userRequest.password,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getLoginRx())
    }

    @SuppressLint("CheckResult")
    fun userCreateEmail(userRequest: com.reyson.spotd.Class.Activity.SignUp.Email.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.createEmail(
            userRequest.email,
            userRequest.password,
            userRequest.uuid,
            userRequest.code,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCreateEmailRx())
    }

    @SuppressLint("CheckResult")
    fun userCreateFullName(userRequest: com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.createFullName(
            userRequest.uid,
            userRequest.first_name,
            userRequest.middle_name,
            userRequest.last_name,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCreateFullNameRx())
    }

    @SuppressLint("CheckResult")
    fun userCreateUserName(userRequest: com.reyson.spotd.Class.Activity.SignUp.Username.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.createUserName(
            userRequest.uid,
            userRequest.username,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCreateUserNameRx())
    }

    @SuppressLint("CheckResult")
    fun userCreateProfilePicture(userRequest: com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.createProfilePicture(
            userRequest.uid,
            userRequest.filename,
            userRequest.extension,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCreateProfilePictureRx())
    }

    @SuppressLint("CheckResult")
    fun userCreatePost(userRequest: com.reyson.spotd.Class.Activity.Create.Upload.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.createPost(
            userRequest.uid,
            userRequest.imageName,
            userRequest.imageStatus,
            userRequest.caption,
            userRequest.status,
            userRequest.commentStats,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getCreatePostRx())
    }
    @SuppressLint("CheckResult")
    fun userFetchPost(userRequest: com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.fetchForyou(
            userRequest.uid,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFetchForyouRx())
    }
    @SuppressLint("CheckResult")
    fun userFetchNotification(userRequest: com.reyson.spotd.Class.Activity.Notification.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.fetchNotification(
            userRequest.uid,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFetchNotificationRx())
    }

    @SuppressLint("CheckResult")
    fun userFetchProfile(userRequest: com.reyson.spotd.Class.Activity.Profile.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.fetchProfile(
            userRequest.uid,
            userRequest.user,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFetchProfileRx())
    }
    @SuppressLint("CheckResult")
    fun userFetchProfilePost(userRequest: com.reyson.spotd.Class.Fragments.Profile.Post.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.fetchProfilePost(
            userRequest.uid,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFetchProfilePostRx())
    }

    @SuppressLint("CheckResult")
    fun userFetchPost(userRequest: com.reyson.spotd.Class.Activity.Posts.Image.Model.Request) {
        val retroService = RetroInstance.getRetroInstance().create(UserApi::class.java)
        retroService.fetchPost(
            userRequest.uid,
            userRequest.push_key,
            loadTOKEN()
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getFetchPostRx())
    }
    private fun getLoginRx(): Observer<Response> {
        return object : Observer<Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Sign IN", "onSubscribe: ")
            }

            override fun onNext(t: Response) {
                loginLiveData.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.e("Sign IN", "Error occurred: ${e.message}")
                loginLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Sign IN", "onComplete: ")
            }


        }
    }

    private fun getCreateEmailRx(): Observer<com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Email", "onSubscribe: ")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response) {
                createEmailLiveData.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.e("Create Email", "Error occurred: ${e.toString()}")
                createEmailLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Email", "onComplete: ")
            }
        }
    }

    private fun getCreateFullNameRx(): Observer<com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create FullName", "onSubscribe: ")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response) {
                createFullNameLiveData.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.e("Create FullName", "Error occurred: ${e.message}")
                createFullNameLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create FullName", "onComplete: ")
            }
        }
    }

    private fun getCreateUserNameRx(): Observer<com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Username", "onSubscribe: ")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response) {
                createUserNameLiveData.postValue(t)
            }

            override fun onError(e: Throwable) {
                Log.e("Create Username", "Error occurred: ${e.message}")
                createUserNameLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Username", "onComplete: ")
            }
        }
    }

    private fun getCreateProfilePictureRx(): Observer<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response> {
        return object :
            Observer<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Profile Picture", "onSubscribe: ")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response) {
                val gson = Gson()
                val responseJson = gson.toJson(t)
                val responseObject = gson.fromJson(responseJson, com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response::class.java)
                createProfilePictureLiveData.postValue(responseObject)
            }

            override fun onError(e: Throwable) {
                Log.e("Create Profile Picture", "Error occurred: ${e.message}")
                createProfilePictureLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Profile Picture", "onComplete: ")
            }
        }
    }

    private fun getCreatePostRx(): Observer<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            // override fun onNext(t: com.reyson.spotd.Class.Activity.Create.Upload.Model.Response) {
            //     createPostLiveData.postValue(t)
            // }
            override fun onNext(t: com.reyson.spotd.Class.Activity.Create.Upload.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                createPostLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                createPostLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }
    private fun getFetchForyouRx(): Observer<com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            override fun onNext(t: com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                fetchForyouLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                fetchForyouLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }
    private fun getFetchNotificationRx(): Observer<com.reyson.spotd.Class.Activity.Notification.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.Notification.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.Notification.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                fetchNotificationLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                fetchNotificationLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }

    private fun getFetchProfileRx(): Observer<com.reyson.spotd.Class.Activity.Profile.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.Profile.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.Profile.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                fetchProfileLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                fetchProfileLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }
    private fun getFetchProfilePostRx(): Observer<com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            override fun onNext(t: com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                fetchProfilePostLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                fetchProfilePostLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }

    private fun getFetchPostRx(): Observer<com.reyson.spotd.Class.Activity.Posts.Image.Model.Response> {
        return object : Observer<com.reyson.spotd.Class.Activity.Posts.Image.Model.Response> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Create Post", "onSubscribe: $d")
            }

            override fun onNext(t: com.reyson.spotd.Class.Activity.Posts.Image.Model.Response) {
                Log.d("Create Post", "onSubscribe: ")
                fetchPostLiveData.postValue(t)
            }


            override fun onError(e: Throwable) {
                Log.e("Create Post", "Error occurred: $e")
                fetchPostLiveData.postValue(null)
            }

            override fun onComplete() {
                Log.d("Create Post", "onComplete: ")
            }
        }
    }
}