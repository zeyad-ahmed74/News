plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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

kapt{
    correctErrorTypes = true
    generateStubs = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")



    // sdp library
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
    // ssp
    implementation ("com.intuit.ssp:ssp-android:1.1.0")


    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Date/Time
    implementation ("org.ocpsoft.prettytime:prettytime:4.0.1.Final")

    // Coil
    implementation("io.coil-kt:coil:2.5.0")


    // Circle Image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // View Model
    val lifecycleVersion1 = "2.2.0"
    // ViewModel and LiveData
    implementation ("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion1")

    // Navigation

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:2.7.6")

    // View Model


    val lifecycleVersion = "2.6.2"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")


    // Optional


    // Saved state module for ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion")



    // Coroutines

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lottie animation
    implementation ("com.airbnb.android:lottie:6.2.0")

    // Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // View Pager
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Circle Indicator
    implementation ("me.relex:circleindicator:2.1.6")

    // ken burns view
    implementation("com.flaviofaria:kenburnsview:1.0.6")

    // Swipe Refresh
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // Shimmer
    implementation ("com.facebook.shimmer:shimmer:0.5.0")

    val multidexVersion = "2.0.1"
    implementation ("androidx.multidex:multidex:$multidexVersion")

    // Room DB

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")






}