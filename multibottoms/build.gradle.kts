plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.vanniktech)
}

android {
    namespace = "io.selimdawa.multibottoms"
    compileSdk = 37

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

mavenPublishing {
    coordinates(groupId = "io.github.selimdawa", artifactId = "multi-bottoms", version = "1.0.0-beta")

    // publishToMavenCentral(automaticRelease = true)

    // if (!System.getenv("JITPACK").isNullOrEmpty()) {
    //     // Skip signing on JitPack
    // } else {
    //     signAllPublications()
    // }

    pom {
        name.set("Bubble Bottom")
        description.set("A customizable Android Bubble Bottom Navigation library with smooth animations and modern UI design.")

        url.set("https://github.com/selimdawa/MultiBottoms")

        licenses {
            license {
                name.set("Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("selimdawa")
                name.set("Selim Dawa")
                email.set("selimdawa@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/selimdawa/MultiBottoms")
            connection.set("scm:git:https://github.com/selimdawa/MultiBottoms.git")
            developerConnection.set("scm:git:ssh://git@github.com:selimdawa/MultiBottoms.git")
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.interpolator)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.coroutines.android)

    // Navigation Architecture Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}