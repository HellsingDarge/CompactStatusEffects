plugins {
    id("fabric-loom")
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

val jarBaseName: String by project

base {
    archivesName.set(jarBaseName)
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
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.44.0+1.18") // In case any mods pulls in incompatible Fabric version during dev

    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
    modImplementation("com.terraformersmc:modmenu:$modMenuVersion")
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothVersion") {
        exclude(module = "net.fabricmc.fabric-api")
    }
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:7.0.346")
    modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-fabric:7.0.346")
}

val mainSourceSet: SourceSet = the<SourceSetContainer>()["main"]

loom {
    accessWidenerPath.set(mainSourceSet.resources.first { it.name == "compactstatuseffects.accessWidener" })
}

tasks.named<ProcessResources>("processResources") {
    copy {
        val enGB = mainSourceSet.resources.filter { "lang" in it.path }.first { it.name == "en_gb.json" }.toPath()
        from(enGB)
        rename {
            "en_us.json"
        }
        into(enGB.parent)
    }
    dependsOn(fillVersions)
}

tasks.named<JavaCompile>("compileJava") {
    dependsOn(fillVersions)
    options.encoding = "UTF-8"
    options.release.set(17)
}

tasks.named<Jar>("jar") {
    from("LICENCE")
    exclude("_fabric.mod.json")
}

tasks.named<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

val fillVersions = tasks.register<Copy>("fillVersions") {
    inputs.file("gradle.properties")

    val template = mainSourceSet.resources.first { it.name == "_fabric.mod.json" }.toPath()

    from(template) {
        val minecraftVersion: String by project
        val fabricKotlinVersion: String by project
        val clothVersion: String by project
        val modMenuVersion: String by project

        expand(
            mutableMapOf(
                "mod_ver" to project.version,
                "minecraft_ver" to minecraftVersion,
                "fabric_kotlin_ver" to fabricKotlinVersion,
                "cloth_ver" to clothVersion,
                "modmenu_ver" to modMenuVersion
            )
        )
    }
    rename {
        "fabric.mod.json"
    }
    into(template.parent)
}