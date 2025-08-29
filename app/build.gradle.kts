plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.reyson.spotd"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.reyson.spotd"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // for retrofit rxjava rxandroid
    // start
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.9.0")
    implementation ("io.reactivex.rxjava2:rxjava:2.2.21")
    implementation ("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation ("com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0")
    // end
    // pinview for code
    implementation("io.github.chaosleung:pinview:1.4.4")
    // json extractor plugins
    implementation("com.google.code.gson:gson:2.10.1")
    // okhttp plugin
    implementation("com.growingio.android:okhttp3:3.5.3")
    // implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    // to load images
    implementation("com.squareup.picasso:picasso:2.71828")
    // plug ins to load rounded images in picasso
    implementation("jp.wasabeef:picasso-transformations:2.4.0")
    // to refresh the screen by swipe down
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    // for client browser for link internal links
    implementation("androidx.browser:browser:1.7.0")
    //
    implementation("com.google.mlkit:image-labeling-common:18.1.0")
    implementation("com.google.mlkit:image-labeling-default-common:17.0.0")
    implementation("com.google.mlkit:object-detection-common:18.0.0")
    implementation("com.google.mlkit:object-detection:17.0.0")

    implementation("com.google.mlkit:image-labeling-custom:17.0.2")
    implementation("com.google.mlkit:image-labeling:17.0.7")
    testImplementation("org.mockito:mockito-core:5.7.0")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    implementation("androidx.palette:palette:1.0.0")

}