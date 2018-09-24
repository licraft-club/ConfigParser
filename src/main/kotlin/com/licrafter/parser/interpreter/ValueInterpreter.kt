package com.licrafter.parser.interpreter

import com.licrafter.parser.AnnotationInterpreter
import com.licrafter.parser.annotation.ConfigValue
import com.licrafter.parser.utils.ChatColorUtils
import com.licrafter.parser.utils.TypeUtil
import org.bukkit.ChatColor
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.HashMap
import java.util.HashSet

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
class ValueInterpreter(private val field: Field) : AnnotationInterpreter {

    override fun decodeFromYml(configuration: ConfigurationSection, targetClass: Class<out Any>): Any? {
        try {
            return if (TypeUtil.isMapType(targetClass)) {
                decodeMapValue(configuration)
            } else {
                decodeSimpleValue(configuration, null)
            }
        } catch (e: Exception) {
            throw RuntimeException("decode value: " + annotation.path + " error:\n " + e)
        }

    }

    private fun decodeMapValue(configuration: ConfigurationSection): Any? {
        val genericType = field.genericType
        val map = HashMap<String, Any>()
        if (genericType != null && genericType is ParameterizedType) {
            val valueClass = genericType.actualTypeArguments[1] as Class<*>
            if (TypeUtil.isBaseType(valueClass)) {
                val keySet = configuration.getConfigurationSection(annotation.path).getKeys(false)
                for (key in keySet) {
                    decodeSimpleValue(configuration, key)?.let {
                        map.put(key, it)
                    }
                }
                return map
            }
        }
        return null
    }

    private fun decodeSimpleValue(configuration: ConfigurationSection, key: String?): Any? {
        if (configuration.contains(annotation.path)) {
            when {
                field.type == ItemStack::class.java -> return configuration.getItemStack(getValuePath(key))
                field.type == Vector::class.java -> return configuration.getVector(getValuePath(key))
                else -> {
                    var value = configuration.get(getValuePath(key))
                    if (annotation.colorChar != ' ') {
                        if (value is String) {
                            value = ChatColor.translateAlternateColorCodes(annotation.colorChar, value)
                        }

                        if (value is List<*>) {
                            return value.map {
                                if (it is String) {
                                    ChatColor.translateAlternateColorCodes(annotation.colorChar, it)
                                } else {
                                    it
                                }
                            }
                        }
                    }
                    return value
                }
            }
        } else if (!annotation.default.isEmpty()) {
            return annotation.default
        }
        return null
    }

    override fun encodeToYml(configuration: ConfigurationSection, target: Any) {
        try {
            val targetClass = target.javaClass
            if (TypeUtil.isMapType(targetClass)) {
                saveMapValue(configuration, target)
            } else {
                saveSimpleValue(configuration, target, null)
            }
        } catch (e: Exception) {
            throw RuntimeException("encode value: " + annotation.path + " error:\n" + e)
        }

    }

    private fun saveMapValue(configuration: ConfigurationSection, target: Any) {
        if (!TypeUtil.isMapType(target.javaClass)) {
            return
        }
        val map = target as Map<*, *>
        val genericType = field.genericType
        if (genericType != null && genericType is ParameterizedType) {
            val valueClass = genericType.actualTypeArguments[1] as Class<*>
            val removeSet = HashSet<String>()
            removeSet.addAll(configuration.getConfigurationSection(annotation.path).getKeys(false))
            removeSet.removeAll(map.keys)
            for (key in removeSet) {
                saveSimpleValue(configuration, null, key)
            }
            if (TypeUtil.isBaseType(valueClass)) {
                map.keys.filterIsInstance<String>()
                        .forEach { saveSimpleValue(configuration, map[it], it) }
            }
        }
    }

    private fun saveSimpleValue(configuration: ConfigurationSection, target: Any?, key: String?) {
        var temp = target
        if (annotation.colorChar != ' ') {
            if (temp is String) {
                temp = ChatColorUtils.encodeAlternateColorCodes(annotation.colorChar, temp)
            }
            if (temp is List<*>) {
                temp.map {
                    if (it is String) {
                        ChatColorUtils.encodeAlternateColorCodes(annotation.colorChar, it)
                    } else {
                        it
                    }
                }
            }
        }
        configuration.set(getValuePath(key), temp)
    }

    private fun getValuePath(key: String?): String {
        return if (key == null) annotation.path else annotation.path + "." + key
    }

    val annotation = field.getAnnotation(ConfigValue::class.java)
}