import xyz.srnyx.gradlegalaxy.data.config.DependencyConfig
import xyz.srnyx.gradlegalaxy.data.config.JavaSetupConfig
import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
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
    version = "2.2.1",
    description = "General essentials for Venox Network"),
    annoyingAPIConfig = DependencyConfig(version = "be87b43"))

repository(Repository.PLACEHOLDER_API, Repository.MULTIVERSE)
dependencies {
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("org.mvplugins.multiverse.core:multiverse-core:5.6.2")
}
