plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraft_version = project.properties["minecraft_version"] as String
val fabric_version = project.properties["fabric_api_version"] as String

sourceSets.main.get().resources.srcDir(file("src/main/generated/resources"))

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentFabric").extendsFrom(configurations["common"])
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")
    modApi("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_api_version"]}+$minecraft_version")
    modApi("dev.architectury:architectury-fabric:${project.properties["architectury_version"]}")

    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionFabric")) { isTransitive = false }

    //Cloth Config
    include(modApi("me.shedaniel.cloth:cloth-config-fabric:${project.properties["cloth_config_version"]}")!!)

    //Cobblemon
    modApi("com.cobblemon:fabric:${project.properties["cobblemon_version"]}+$minecraft_version")

   // modApi("com.github.Virtuoel:Pehkui:${project.properties["pehkui_version"]}-$minecraft_version", {
   //     exclude( group= "net.fabricmc.fabric-api")
   // })
   // modCompileOnly ("dev.emi:emi-fabric:${project.properties["emi_version"]}+$minecraft_version:api")
   // modLocalRuntime ("dev.emi:emi-fabric:${project.properties["emi_version"]}+$minecraft_version")
}

tasks {
    base.archivesName.set(base.archivesName.get() + "-fabric")
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("generations/gg/generations/core/generationscore/fabric/datagen/**")
        exclude("data/forge/**")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
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
