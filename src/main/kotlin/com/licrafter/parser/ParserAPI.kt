package com.licrafter.parser

import com.licrafter.parser.annotation.ConfigBean
import com.licrafter.parser.interpreter.BeanInterpreter
import com.licrafter.parser.utils.DataConfigFile
import org.bukkit.plugin.java.JavaPlugin

/**
 * Created by shell on 2018/9/23.
 * <p>
 * Gmail: shellljx@gmail.com
 */
object ParserAPI {

    var debug = false

    /**
     * load config values
     */
    fun loadConfig(plugin: JavaPlugin, vararg values: Any) {
        values.forEach {
            loadConfig(plugin, it)
        }
    }

    /**
     * load config value
     */
    fun loadConfig(plugin: JavaPlugin, value: Any) {
        loadConfig(plugin, null, value)
    }

    /**
     * load config value with specific path
     */
    fun loadConfig(plugin: JavaPlugin, filePath: String?, value: Any) {
        val newValue = BeanInterpreter().decodeFromYml(initConfig(plugin, filePath, value).getConfig(), value::class.java)
        value::class.java.declaredFields.forEach {
            it.set(value, it.get(newValue))
        }
    }

    /**
     * save config values
     */
    fun saveConfig(plugin: JavaPlugin, vararg values: Any) {
        values.forEach {
            saveConfig(plugin, it)
        }
    }

    /**
     * save config value
     */
    fun saveConfig(plugin: JavaPlugin, value: Any) {
        saveConfig(plugin, null, value)
    }

    /**
     * save config value to specific path
     */
    fun saveConfig(plugin: JavaPlugin, filePath: String?, value: Any) {
        val configuration = initConfig(plugin, filePath, value)
        BeanInterpreter().encodeToYml(configuration.getConfig(), value)
        configuration.saveConfig()
    }

    private fun initConfig(plugin: JavaPlugin, configFilePath: String?, target: Any): DataConfigFile {
        val configFileName = configFilePath ?: target::class.java.getAnnotation(ConfigBean::class.java).file
        return DataConfigFile(plugin, configFileName)
    }
}