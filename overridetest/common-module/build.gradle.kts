plugins {
  kotlin("multiplatform")
  id("app.cash.molecule")
}

kotlin {
  jvm { withJava() }
  // TODO: No native targets yet for Molecule until Compose 1.2.0 available in JB KMP runtime.
  // ios()

  sourceSets {
    all {
      languageSettings.apply {
        optIn("kotlin.RequiresOptIn")
      }
    }
    val commonMain by getting {
      dependencies {
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
      }
    }
  }
}

extensions.getByType(JavaPluginExtension::class).apply {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}