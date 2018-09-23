package com.licrafter.parser

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

    }

    /**
     * load config value with specific path
     */
    fun loadConfig(plugin: JavaPlugin, filePath: String, value: Any) {

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
}