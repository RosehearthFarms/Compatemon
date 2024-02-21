architectury {
    common("forge", "fabric")
    platformSetupLoomIde()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // Cobblemon, Pehkui, ClothConfig
    // Cloth Config no longer needed

    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation ("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")
    // modApi ("dev.architectury:architectury:${project.properties["architectury_version"]}")
    // modImplementation("dev.architectury:architectury:${project.properties["architectury_version"]}")

    modApi("com.cobblemon:mod:${project.properties["cobblemon_version"]}+${project.properties["minecraft_version"]}")
    modApi("com.github.Virtuoel:Pehkui:${project.properties["pehkui_version"]}-${project.properties["minecraft_version"]}-forge")
    modApi("me.shedaniel.cloth:cloth-config:${project.properties["cloth_config_version"]}")
    //modApi("org.valkyrienskies:valkyrienskies-118-common:${project.properties["vs2_version"]}")
}


tasks {

    processResources {
        inputs.property("version", project.properties["mod_version"])

        filesMatching("*.mixins.json"){
            expand(mapOf("minVersion" to project.properties["mod_version"]))
        }
    }

}
