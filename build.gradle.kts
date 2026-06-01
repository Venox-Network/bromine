import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.utility.setupAnnoyingAPI
import xyz.srnyx.gradlegalaxy.utility.spigotAPI


plugins {
    java
    id("xyz.srnyx.gradle-galaxy") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.9"
}

spigotAPI(config = DependencyConfig(version = "1.8.8"))
setupAnnoyingAPI(javaSetupConfig = JavaSetupConfig(
    group = "network.venox",
    version = "3.0.0",
    description = "General essentials for Venox Network"),
    annoyingAPIConfig = DependencyConfig(version = "db46b2b"))
