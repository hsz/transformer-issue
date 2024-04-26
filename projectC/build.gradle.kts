plugins {
    `kotlin-dsl`
    id("gradle-plugin")
}

repositories {
    maven("https://www.jetbrains.com/intellij-repository/releases")
    mavenCentral()
}

dependencies {
    intellijPlatformDependency("com.jetbrains.intellij.idea:ideaIC:2024.1")
}

kotlin {
    jvmToolchain(17)
}
