plugins {
    id("fabric-loom")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val jarBaseName: String by project

base {
    archivesBaseName = jarBaseName
}

val mavenGroup: String by project
val modVersion: String by project
group = mavenGroup
version = modVersion

repositories {
    maven(url = "https://maven.fabricmc.net/")
    maven(url = "https://maven.terraformersmc.com/releases")
    maven(url = "https://maven.shedaniel.me/")
}

dependencies {
    val minecraftVersion: String by project
    val yarnMappings: String by project
    val loaderVersion: String by project
    val fabricKotlinVersion: String by project
    val clothVersion: String by project
    val modMenuVersion: String by project

    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings:v2")

    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
    modImplementation("com.terraformersmc:modmenu:$modMenuVersion")
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(module = "net.fabricmc.fabric-api")
    }
}

tasks.getByName<ProcessResources>("processResources") {
    filesMatching("fabric.mod.json") {
        expand(
            // Must be mutable, otherwise it crashes with "couldn't copy file" due "Unsupported operation"
            mutableMapOf(
               "version" to project.version
            )
        )
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    dependsOn("createUSLang")
}

tasks.getByName<Jar>("jar") {
    from("LICENCE")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

val sourceSet = the<SourceSetContainer>()

tasks.register("createUSLang") {
    doFirst {
        val langFiles = sourceSet["main"].resources.filter { "lang" in it.absolutePath }.files
        val enGB = langFiles.first { it.name == "en_gb.json" }
        val enUS = langFiles.firstOrNull { it.name == "en_us.json" } ?: File(enGB.parent, "en_us.json")
        enGB.copyTo(enUS, true)
    }
}