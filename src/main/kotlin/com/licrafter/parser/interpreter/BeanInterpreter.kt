package com.licrafter.parser.interpreter

import com.licrafter.parser.AnnotationInterpreter
import com.licrafter.parser.annotation.ConfigSection
import com.licrafter.parser.annotation.ConfigValue
import org.bukkit.configuration.ConfigurationSection
import java.lang.reflect.Field

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
class BeanInterpreter : AnnotationInterpreter {

    override fun decodeFromYml(configuration: ConfigurationSection, targetClass: Class<out Any>): Any? {
        try {
            val target = targetClass.newInstance()
            for (field in targetClass.declaredFields) {
                field.isAccessible = true
                val interpreter = getInterpreter(field) ?: continue
                val fieldConfiguration = getFieldConfiguration(interpreter, configuration) ?: continue
                val fieldValue = interpreter.decodeFromYml(fieldConfiguration, field.type) ?: continue
                field.set(target, fieldValue)
            }
            return target
        } catch (e: Exception) {
            throw RuntimeException("decode bean " + targetClass.name + " error:\n" + e)
        }

    }


    override fun encodeToYml(configuration: ConfigurationSection, target: Any) {
        val targetClass = target.javaClass
        try {
            for (field in targetClass.declaredFields) {
                field.isAccessible = true
                val interpreter = getInterpreter(field) ?: continue
                val fieldConfiguration = getFieldConfiguration(interpreter, configuration) ?: continue
                interpreter.encodeToYml(fieldConfiguration, field.get(target))
            }
        } catch (e: Exception) {
            throw RuntimeException("encode bean " + targetClass.name + "error:\n" + e)
        }

    }


    private fun getInterpreter(field: Field): AnnotationInterpreter? {
        val configValue = field.getAnnotation(ConfigValue::class.java)
        val configSection = field.getAnnotation(ConfigSection::class.java)

        return when {
            configValue != null -> ValueInterpreter(field)
            configSection != null -> SectionInterpreter(field)
            else -> null
        }
    }

    private fun getFieldConfiguration(interpreter: AnnotationInterpreter, configuration: ConfigurationSection): ConfigurationSection? {
        return when (interpreter) {
            is ValueInterpreter -> configuration
            is SectionInterpreter -> {
                val path = interpreter.annotation.path
                if (!configuration.contains(path)) {
                    configuration.createSection(path)
                }
                configuration.getConfigurationSection(path)
            }
            else -> null
        }
    }
}