
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

    forge {
        mixinConfig ("${project.properties["mod_id"]}-common.mixins.json")
        mixinConfig ("${project.properties["mod_id"]}.mixins.json")
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
    maven("https://maven.shadowsoffire.dev/releases")
  //  maven("https://code.redspace.io/releases")
    maven("https://maven.theillusivec4.top")
    maven("https://maven.kosmx.dev/")
    maven("https://maven.enginehub.org/repo/")

}

dependencies {
    //General Dependencies
    forge("net.minecraftforge:forge:${minecraft_version}-${project.properties["forge_version"]}")

    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionForge")) { isTransitive = false }

    modImplementation("com.cobblemon:forge:${project.properties["cobblemon_version"]}+$minecraft_version")
    runtimeOnly("maven.modrinth:ordsPcFz:${project.properties["kotlin_forge_version"]}") //kotlinforforge

    //include(modApi("me.shedaniel.cloth:cloth-config-forge:${project.properties["cloth_config_version"]}")!!)

// Compatemon Dependencies

    modApi("com.github.Virtuoel:Pehkui:${project.properties["pehkui_forge"]}-${minecraft_version}-forge")


    // curios
    //implementation("top.theillusivec4.curios:curios-forge:${project.properties["curios_version"]}")
    // apotheosis
    //implementation("curse.maven:apotheosis-313970:4874713")
    modImplementation("dev.shadowsoffire:Apotheosis:${minecraft_version}-${project.properties["apotheosis_version"]}")
    modImplementation("dev.shadowsoffire:Placebo:${minecraft_version}-${project.properties["placebo_version"]}")
    modImplementation("dev.shadowsoffire:ApothicAttributes:${minecraft_version}-${project.properties["apothic_attributes_version"]}")
    // IRONS SPELLS N SPELLBOOKS **********************************************************************************
   // modImplementation("io.redspace.ironsspellbooks:irons_spellbooks:${minecraft_version}-${project.properties["irons_spells_version"]}")

    // GECKOLIB ***************************************************************************************************
    //runtimeOnly("software.bernie.geckolib:geckolib-forge-${minecraft_version}:${project.properties["geckolib_version"]}")

    // CAELUS *****************************************************************************************************
    //runtimeOnly("top.theillusivec4.caelus:caelus-forge:${project.properties["caelus_version"]}")

    // PLAYER ANIMATOR ********************************************************************************************
    //runtimeOnly("dev.kosmx.player-anim:player-animation-lib-forge:${minecraft_version}-${project.properties["player_animator_version"]}")

    // TETRA ******************************************************************************************************
    //compileOnly("se.mickelus.mutil:mutil:${minecraft_version}-${project.properties["mutil_version"]}")
   // compileOnly("curse.maven:tetra-${project.properties["tetra_version"]}")

    // BETTER COMBAT **********************************************************************************************
    //runtimeOnly fg.deobf("curse.maven:better-combat-by-daedelus-${better_combat_version}")

    // PATCHOULI **************************************************************************************************
    //runtimeOnly fg.deobf("vazkii.patchouli:Patchouli:${minecraft_version}-${patchouli_version}")

    // JSON ******************************************************************************************************
   // implementation("com.google.code.gson:gson:${project.properties["gson_version"]}")

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
            expand(mapOf("modId" to project.properties["mod_id"]))
            expand(mapOf("displayName" to project.properties["mod_name"]))
            expand(mapOf("authors" to project.properties["mod_authors"]))
            expand(mapOf("description" to project.properties["mod_description"]))
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
