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

    }

    /**
     * save config value
     */
    fun saveConfig(plugin: JavaPlugin, value: Any) {

    }

    /**
     * save config value to specific path
     */
    fun saveConfig(plugin: JavaPlugin, filePath: String, value: Any) {

    }

    private fun initConfig(plugin: JavaPlugin, configFilePath: String?, target: Any): DataConfigFile {
        val configFileName: String
        configFileName = if (configFilePath != null) {
            configFilePath
        } else {
            val configBean = target::class.java.getAnnotation(ConfigBean::class.java)
            configBean.file
        }
        return DataConfigFile(plugin, configFileName)
    }
}