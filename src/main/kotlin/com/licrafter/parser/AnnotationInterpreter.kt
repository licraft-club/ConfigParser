package com.licrafter.parser

import org.bukkit.configuration.ConfigurationSection

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
interface AnnotationInterpreter {

    fun decodeFromYml(configuration: ConfigurationSection, targetClass: Class<out Any>): Any?

    fun encodeToYml(configuration: ConfigurationSection, target: Any)
}