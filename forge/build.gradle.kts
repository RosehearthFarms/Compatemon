
plugins {
   // id("net.minecraftforge.gradle") version("[6.0,6.2)")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.github.johnrengelman.shadow") version "8.1.1"

    //id("org.spongepowered.mixin") version "0.7.+"
}

val minecraft_version = project.properties["minecraft_version"] as String

architectury {
    platformSetupLoomIde()
    forge()
}

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentForge").extendsFrom(configurations["common"])
}

loom {
    enableTransitiveAccessWideners.set(true)
    silentMojangMappingsLicense()


    mixin {
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }
}

repositories {
    mavenCentral()
    maven("https://maven.minecraftforge.net/")
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://api.modrinth.com/maven")
    maven("https://www.cursemaven.com")
    maven("https://jitpack.io")
}

dependencies {
    //General Dependencies
    //minecraft("net.minecraft:minecraft:$minecraft_version")
    forge("net.minecraftforge:forge:${minecraft_version}-${project.properties["forge_version"]}")

    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionForge")) { isTransitive = false }

    modImplementation("com.cobblemon:forge:${project.properties["cobblemon_version"]}+$minecraft_version")
    runtimeOnly("maven.modrinth:ordsPcFz:${project.properties["kotlin_forge_version"]}") //kotlinforforge

    include(modApi("me.shedaniel.cloth:cloth-config-forge:${project.properties["cloth_config_version"]}")!!)


    // Cobblemon Sizes Dependencies

    //Pehkui
    //implementation("com.github.Virtuoel:Pehkui:${project.properties["pehkui_version"]}-${minecraft_version}")
    modApi("com.github.Virtuoel:Pehkui:${project.properties["pehkui_version"]}-${minecraft_version}-forge")
    //Jade, JEI, and EMI
   // modImplementation ("curse.maven:jade-forge-324717:${project.properties["jade_forge_version"]}")
  // modImplementation ("curse.maven:jei-forge-238222:${project.properties["jei_version}"]}")
  //  compileOnly ("dev.emi:emi-forge:${project.properties["emi_version"]}+$minecraft_version:api")
  //  runtimeOnly ("dev.emi:emi-forge:${project.properties["emi_version"]}+$minecraft_version"))
}

tasks {
    base.archivesName.set(base.archivesName.get() + "-forge")
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("fabric.mod.json")
        exclude("generations/gg/generations/core/generationscore/forge/datagen/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }

    jar.get().archiveClassifier.set("dev")

    sourcesJar {
        val commonSources = project(":common").tasks.sourcesJar
        dependsOn(commonSources)
        from(commonSources.get().archiveFile.map { zipTree(it) })
    }
}

components {
    java.run {
        if (this is AdhocComponentWithVariants)
            withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) { skip() }
    }
}
