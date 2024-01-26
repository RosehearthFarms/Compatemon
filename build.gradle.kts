import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {

    id("java")
    id("java-library")
    id("architectury-plugin") version "3.4-SNAPSHOT"
    kotlin("jvm") version ("1.9.10")
    id("dev.architectury.loom") version "1.3.357" apply false
    idea
}

val minecraft_version = project.properties["minecraft_version"] as String

architectury.minecraft = minecraft_version

subprojects {
    apply(plugin = "dev.architectury.loom")
    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://maven.impactdev.net/repository/development/")
        maven("https://maven.parchmentmc.org")
        maven("https://maven.terraformersmc.com/")
        maven("https://api.modrinth.com/maven")
        maven("https://www.cursemaven.com")
        maven( "https://repo.spongepowered.org/repository/maven-public/" )
        maven("https://maven.shedaniel.me/")
        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven")
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }
    }

    @Suppress("UnstableApiUsage")
    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraft_version")
        "mappings"(loom.layered{
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-$minecraft_version:${project.properties["parchment"]}@zip")
        })

        compileOnly("org.jetbrains:annotations:${project.properties["jetbrains_annotations_version"]}")
    }
    loom.silentMojangMappingsLicense()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "idea")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    version = project.properties["mod_version"] as String
    group = project.properties["maven_group"] as String
    base.archivesName.set(project.properties["mod_id"] as String)

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    java.withSourcesJar()
}

kotlin.jvmToolchain(17)
dependencies {
    implementation(kotlin("stdlib"))
}
repositories {
    mavenCentral()
}