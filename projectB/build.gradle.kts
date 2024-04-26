plugins {
    id("java")
    id("gradle-plugin")
}

repositories {
    maven("https://www.jetbrains.com/intellij-repository/releases")
    mavenCentral()
}

dependencies {
    fooDependency("com.jetbrains.intellij.idea:ideaIC:2024.1")
}
