package com.licrafter.parser.utils

import com.google.common.base.Charsets
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.logging.Level

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
class DataConfigFile(private val plugin: JavaPlugin, private val filename: String) {

    private val dataConfigFile = File(plugin.dataFolder, filename)
    private var newDataConfig: FileConfiguration? = null

    fun reloadConfig() {
        newDataConfig = YamlConfiguration.loadConfiguration(dataConfigFile)

        val defInputStream = plugin.getResource(filename) ?: return

        val streamReader = InputStreamReader(defInputStream, Charsets.UTF_8)

        newDataConfig?.defaults = YamlConfiguration.loadConfiguration(streamReader)

        try {
            defInputStream.close()
        } catch (e: IOException) {
            plugin.logger.log(Level.INFO, plugin.name, "Faild close DataConfigFile inputStream!")
        }

        try {
            streamReader.close()
        } catch (e: IOException) {
            plugin.logger.log(Level.INFO, plugin.name, "Faild close DataConfigFile streamReader!")
        }

    }

    fun getConfig(): FileConfiguration {
        newDataConfig ?: reloadConfig()
        return newDataConfig!!
    }

    fun saveConfig() {
        if (newDataConfig == null) {
            return
        }
        try {
            getConfig().save(dataConfigFile)
        } catch (ex: IOException) {
            plugin.logger.log(Level.INFO, plugin.name, "Could not save config to " + dataConfigFile + ex)
        }

    }

    fun saveDefaultConfig() {
        if (!dataConfigFile.exists()) {
            plugin.saveResource(filename, false)
        }
    }
}