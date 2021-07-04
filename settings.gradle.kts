pluginManagement {
    repositories {
        mavenCentral()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }

    plugins {
        val loomVersion: String by settings
        val kotlinVersion: String by settings

        id("fabric-loom") version loomVersion
        kotlin("jvm") version kotlinVersion
    }

}
