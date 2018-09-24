package com.licrafter.parser.interpreter

import com.licrafter.parser.AnnotationInterpreter
import com.licrafter.parser.annotation.ConfigSection
import com.licrafter.parser.utils.TypeUtil
import org.bukkit.configuration.ConfigurationSection
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.HashMap
import java.util.HashSet

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
class SectionInterpreter(private val field: Field) : AnnotationInterpreter {

    override fun decodeFromYml(configuration: ConfigurationSection, targetClass: Class<out Any>): Any? {
        try {
            if (TypeUtil.isMapType(targetClass)) {
                val map = HashMap<Any, Any>()
                val genericType = field.genericType
                if (genericType != null && genericType is ParameterizedType) {
                    val valueClass = genericType.actualTypeArguments[1] as Class<*>
                    if (!TypeUtil.isBaseType(valueClass)) {
                        val keySet = configuration.getKeys(false)
                        for (key in keySet) {
                            val interpreter = BeanInterpreter()
                            val value = interpreter.decodeFromYml(configuration.getConfigurationSection(key), valueClass)
                            if (value != null) {
                                map.put(key, value)
                            }
                        }
                        return map
                    }
                }
            } else if (!TypeUtil.isBaseType(targetClass)) {
                val interpreter = BeanInterpreter()
                val value = interpreter.decodeFromYml(configuration, targetClass)
                if (value != null) {
                    return value
                }
            }
            return null
        } catch (e: Exception) {
            throw RuntimeException("decode path: " + annotation.path + " error:\n" + e)
        }

    }

    override fun encodeToYml(configuration: ConfigurationSection, target: Any) {
        if (TypeUtil.isMapType(target.javaClass)) {
            val map = target as Map<*, *>
            val genericType = field.genericType
            if (genericType != null && genericType is ParameterizedType) {
                val valueClass = genericType.actualTypeArguments[1] as Class<*>
                val removeKeyset = HashSet<String>()
                removeKeyset.addAll(configuration.getKeys(false))
                removeKeyset.removeAll(map.keys)
                for (key in removeKeyset) {
                    configuration.set(key, null)
                }
                if (!TypeUtil.isBaseType(valueClass)) {
                    map.keys.filterIsInstance<String>()
                            .forEach { key ->
                                if (!configuration.contains(key)) {
                                    configuration.createSection(key)
                                }
                                map[key]?.let {
                                    BeanInterpreter().encodeToYml(configuration.getConfigurationSection(key), it)
                                }
                            }
                }
            }
        } else if (!TypeUtil.isBaseType(target.javaClass)) {
            val interpreter = BeanInterpreter()
            interpreter.encodeToYml(configuration, target)
        }
    }

    val annotation: ConfigSection = field.getAnnotation(ConfigSection::class.java)
}