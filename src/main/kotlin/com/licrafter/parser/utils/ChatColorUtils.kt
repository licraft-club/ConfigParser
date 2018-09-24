package com.licrafter.parser.utils

import org.bukkit.ChatColor

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
object ChatColorUtils {

    /**
     * @param altColorChar
     * @param textToEncode
     * @return
     */
    fun encodeAlternateColorCodes(altColorChar: Char, textToEncode: String): String {
        val b = textToEncode.toCharArray()
        for (i in 0 until b.size - 1) {
            if (b[i] == ChatColor.COLOR_CHAR && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = altColorChar
                b[i + 1] = Character.toLowerCase(b[i + 1])
            }
        }
        return String(b)
    }
}