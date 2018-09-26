package com.licrafter.sample;

import com.licrafter.parser.annotation.ConfigBean;
import com.licrafter.parser.annotation.ConfigSection;
import com.licrafter.parser.annotation.ConfigValue;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
@ConfigBean
public class TestConfig {
    @ConfigValue(path = "data.name")
    public String name;

    @ConfigSection(path = "data.server")
    public Server server;
    @ConfigSection(path = "data.players")
    public Map<String, Player> playerMap;
    @ConfigValue(path = "data.points")
    public Map<String, Integer> pointsMap;


    //section players path = data.players
    public static class Player {
        @ConfigValue(path = "ip")
        public String ip;
        @ConfigValue(path = "money")
        public int money;
        @ConfigValue(path = "friends")
        public List<String> friends;
        @ConfigValue(path = "items")
        public List<ItemStack> items;
    }

    //section server path = data.server
    public static class Server {
        @ConfigValue(path = "ip")
        public String ip;
        @ConfigValue(path = "port")
        public int port;
    }
}
