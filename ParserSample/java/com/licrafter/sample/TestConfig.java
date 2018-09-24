package com.licrafter.sample;

import com.licrafter.parser.annotation.ConfigBean;
import com.licrafter.parser.annotation.ConfigValue;

/**
 * Created by shell on 2018/9/24.
 * <p>
 * Gmail: shellljx@gmail.com
 */
@ConfigBean
public class TestConfig {
    @ConfigValue(path = "settings.name")
    public String name;
}
