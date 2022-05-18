description = "ban-ip-disabler"
version = "0.0.1"
group = "network.venox"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT")
}

plugins {
    id("java")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        filesMatching("**/plugin.yml") {
            expand(rootProject.project.properties)
        }
    }
}