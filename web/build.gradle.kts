@file:Suppress("UNUSED_EXPRESSION")

plugins {
    kotlin("js") version "1.7.10"
    id("org.jetbrains.compose")
}

group "com.github.ko610"
version "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    js(IR) {
        browser {
            testTask {
                testLogging.showStandardStreams = true
                useKarma {
                    useChromeHeadless()
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
            dependencies {
                //Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
        }
    }
}

