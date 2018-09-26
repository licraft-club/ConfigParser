package com.licrafter.parser.utils

import java.util.HashMap

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
object TypeUtils {
    private val BASE_TYPES = arrayOf("java.lang.Integer", "java.lang.Double", "java.lang.Float", "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Character", "java.lang.String", "java.util.List")

    fun isBaseType(classZ: Class<*>): Boolean {
        for (type in BASE_TYPES) {
            if (type == classZ.name) {
                return true
            }
        }
        return false
    }

    fun isMapType(targetClass: Class<*>): Boolean {
        return targetClass == Map::class.java || targetClass == HashMap::class.java
    }

}