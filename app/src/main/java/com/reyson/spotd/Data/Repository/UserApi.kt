package com.reyson.spotd.Repository

import com.reyson.spotd.Class.Activity.SignIn.Model.Response
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {

    @FormUrlEncoded
    @POST("signin/signin.php")
    fun signInto(
        @Field("e") displayName: String?,
        @Field("p") password: String?,
        @Field("t") token: String?
    ): Observable<Response>

    @FormUrlEncoded
    @POST("signup/email.php")
    fun createEmail(
        @Field("e") displayName: String?,
        @Field("p") password: String?,
        @Field("u") uuid: String?,
        @Field("c") code: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.SignUp.Email.Model.Response>

    @FormUrlEncoded
    @POST("signup/fullname.php")
    fun createFullName(
        @Field("u") uid: String?,
        @Field("f") firstName: String?,
        @Field("m") middleName: String?,
        @Field("l") lastName: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.SignUp.FullName.Model.Response>

    @FormUrlEncoded
    @POST("signup/username.php")
    fun createUserName(
        @Field("u") uid: String?,
        @Field("un") username: String??,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.SignUp.Username.Model.Response>

    @FormUrlEncoded
    @POST("signup/profile.php")
    fun createProfilePicture(
        @Field("u") uid: String?,
        @Field("m") ms: String?,
        @Field("e") extension: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.SignUp.Profile.Upload.Model.Response>

    @FormUrlEncoded
    @POST("create/post.php")
    fun createPost(
        @Field("u") uid: String?,
        @Field("in") imageName: String?,
        @Field("is") imageStatus: String?,
        @Field("c") caption: String?,
        @Field("s") status: String?,
        @Field("cs") commentStats: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.Create.Upload.Model.Response>

    @FormUrlEncoded
    @POST("foryou/foryou.php")
    fun fetchForyou(
        @Field("u") uid: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Fragments.Home.Foryou.Model.Response>

    @FormUrlEncoded
    @POST("notification/notify.php")
    fun fetchNotification(
        @Field("u") uid: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.Notification.Model.Response>

    @FormUrlEncoded
    @POST("profile/profile.php")
    fun fetchProfile(
        @Field("u") uid: String?,
        @Field("us") user: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.Profile.Model.Response>

    @FormUrlEncoded
    @POST("profile/fragment/post.php")
    fun fetchProfilePost(
        @Field("u") uid: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Fragments.Profile.Post.Model.Response>

    @FormUrlEncoded
    @POST("post/post.php")
    fun fetchPost(
        @Field("u") uid: String?,
        @Field("p") push_key: String?,
        @Field("t") token: String?
    ): Observable<com.reyson.spotd.Class.Activity.Posts.Image.Model.Response>
}