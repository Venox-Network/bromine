import xyz.srnyx.gradlegalaxy.enums.Repository
import xyz.srnyx.gradlegalaxy.enums.repository
import xyz.srnyx.gradlegalaxy.utility.setupAnnoyingAPI
import xyz.srnyx.gradlegalaxy.utility.spigotAPI


plugins {
    java
    id("xyz.srnyx.gradle-galaxy") version "1.1.2"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

setupAnnoyingAPI("4.3.0", "network.venox", "2.2.0", "General essentials for Venox Network")
spigotAPI("1.8.8")
repository(Repository.PLACEHOLDER_API)
repository("https://repo.onarandombox.com/content/groups/public/")
dependencies {
    compileOnly("me.clip", "placeholderapi", "2.11.3")
    compileOnly("com.onarandombox.multiversecore", "Multiverse-Core", "4.3.1")
}
