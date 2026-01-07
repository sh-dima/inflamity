package io.gitlab.shdima.inflamity

import org.bstats.bukkit.Metrics
import de.exlll.configlib.NameFormatters
import de.exlll.configlib.YamlConfigurationProperties
import de.exlll.configlib.YamlConfigurations
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

@Suppress("unused")
class Inflamity : JavaPlugin() {

    private lateinit var config: Config

    override fun onEnable() {
        val configProperties = YamlConfigurationProperties.newBuilder()
            .setNameFormatter(NameFormatters.LOWER_KEBAB_CASE)
            .build()

        val configFile = Path.of(dataFolder.path, "config.yml")
        config = try {
            YamlConfigurations.load(configFile, Config::class.java, configProperties)
        } catch (e: Exception) {
            Config()
        }

        YamlConfigurations.save(configFile, Config::class.java, config, configProperties)

        if (!config.enabled) return

        try {
            Metrics(this, 25135)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}
