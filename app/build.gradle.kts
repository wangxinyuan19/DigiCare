plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.google.firebase.example.digicare"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.google.firebase.example.digicare"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
}

dependencies {

    // Add this line
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    // Firestore
    implementation("com.google.firebase:firebase-firestore:25.1.0")

    // Other Firebase/Play services deps
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:20.2.0")

    // FirebaseUI (for authentication)
    implementation("com.firebaseui:firebase-ui-auth:8.0.1")

    implementation("com.google.firebase:firebase-storage")
}

// Apply the Google services plugin at the end
apply(plugin = "com.google.gms.google-services")
