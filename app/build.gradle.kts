plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    //Kotlin Serializer
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    //Dagger Hilt
    id("com.google.dagger.hilt.android")
    //Kotlin Ksp
    id ("com.google.devtools.ksp")

}

android {
    namespace = "com.dnpstudio.recipecorner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dnpstudio.recipecorner"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Destination
    implementation("io.github.raamcosta.compose-destinations:core:1.9.54")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.54")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

    //Supabase Dependency
    implementation(platform("io.github.jan-tennert.supabase:bom:2.3.1"))
    implementation ("io.github.jan-tennert.supabase:postgrest-kt")
    implementation ("io.github.jan-tennert.supabase:storage-kt")
    implementation ("io.github.jan-tennert.supabase:gotrue-kt")
    implementation ("io.github.jan-tennert.supabase:functions-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")

    //Ktor Engine
    implementation ("io.ktor:ktor-client-okhttp:2.3.10")

    //Coil Image Loader
    implementation("io.coil-kt:coil-compose:2.6.0")

    //Kotpref
    implementation ("com.chibatching.kotpref:kotpref:2.13.1")
    implementation ("com.chibatching.kotpref:initializer:2.13.1")

    //Room Database
    val room_version = "2.5.0"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    //Dagger-Hilt Injection
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //API Wrapper
    implementation("com.github.rmaprojects:apiresponsewrapper:1.5.2")

}