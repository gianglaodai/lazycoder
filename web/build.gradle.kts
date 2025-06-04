val kotlinx_browser_version: String by project

plugins {
    kotlin("multiplatform") version "2.1.10"
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig {
                outputFileName = "leaderboard.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        wasmJsMain.dependencies {
            implementation(npm("htmx.org", "2.0.3"))
            implementation("org.jetbrains.kotlinx:kotlinx-browser:$kotlinx_browser_version")
        }
    }
}
