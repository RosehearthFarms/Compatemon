architectury {
    common("forge", "fabric")
    platformSetupLoomIde()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    modCompileOnly("com.cobblemon:mod:${project.properties["cobblemon_version"]}+${project.properties["minecraft_version"]}")

    modApi("me.shedaniel.cloth:cloth-config:${project.properties["cloth_config_version"]}")
}