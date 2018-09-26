package com.licrafter.sample;

import com.licrafter.parser.ParserAPI;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
public class ParserSample extends JavaPlugin {

    public TestConfig config = new TestConfig();

    @Override
    public void onEnable() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            reloadConfig();
        }
        ParserAPI.INSTANCE.setDebug(true);

        ParserAPI.INSTANCE.loadConfig(this, config);
    }

    @Override
    public void onDisable() {
        ParserAPI.INSTANCE.saveConfig(this, config);
    }
}
