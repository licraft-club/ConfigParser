package com.licrafter.parser.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor

/**
 * Created by shell on 2018/9/26.
 * <p>
 * Gmail: shellljx@gmail.com
 */
internal object LogUtils {
    fun info(key: String, value: Any?) {
        Bukkit.getConsoleSender().sendMessage(getPrefix()
                + ChatColor.translateAlternateColorCodes('&', "&e$key: $value"))
    }

    private fun getPrefix(): String {
        return ChatColor.translateAlternateColorCodes('&', "&a[&6ConfigParser&a]&7")
    }
}