plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins.create("gradle-plugin") {
        id = "gradle-plugin"
        implementationClass = "MyGradlePlugin"
    }
}
