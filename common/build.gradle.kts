architectury {
    common("forge", "fabric")
    platformSetupLoomIde()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // Cobblemon, Pehkui, ClothConfig
    // Cloth Config no longer needed
    modApi("com.cobblemon:mod:${project.properties["cobblemon_version"]}+${project.properties["minecraft_version"]}")
    modApi("com.github.Virtuoel:Pehkui:${project.properties["pehkui_version"]}-${project.properties["minecraft_version"]}-forge")
    modApi("me.shedaniel.cloth:cloth-config:${project.properties["cloth_config_version"]}")

}