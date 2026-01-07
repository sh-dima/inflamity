package org.example.template

//import dev.jorel.commandapi.CommandAPI
//import org.bstats.bukkit.Metrics
//import de.exlll.configlib.NameFormatters
//import de.exlll.configlib.YamlConfigurationProperties
//import de.exlll.configlib.YamlConfigurations
//import dev.jorel.commandapi.CommandAPIPaperConfig
import org.bukkit.plugin.java.JavaPlugin
//import java.nio.file.Path

@Suppress("unused")
class Plugin : JavaPlugin() {

    private val pair = Pair("Hello World!", "Goodbye World!")
//    private lateinit var config: Config

    override fun onEnable() {
        logger.info(pair.first)
        logger.fine("Fine!")
        logger.finer("Finer!")
        logger.finest("Finest!")

//        val configProperties = YamlConfigurationProperties.newBuilder()
//            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
//            .build()

//        val configFile = Path.of(dataFolder.path, "config.yml")
//        config = try {
//            YamlConfigurations.load(configFile, Config::class.java, configProperties)
//        } catch (e: Exception) {
//            Config()
//        }
//
//        YamlConfigurations.save(configFile, Config::class.java, config, configProperties)

//        CommandAPI.onLoad(CommandAPIPaperConfig(this))
//        CommandAPI.onEnable()

//        try {
//            Metrics(this, https://bstats.org/what-is-my-plugin-id)
//        } catch (exception: Exception) {
//            exception.printStackTrace()
//        }
    }

    override fun onDisable() {
        logger.info(pair.second)
    }
}
