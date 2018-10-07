# ConfigParser

The ConfigParser API a simple annotation based config system which is  written in kotlin, it allows you to use provided `saveConfig` and `loadConfig` methods to handle saving and loading of all annotated fields. 
[Source code: https://github.com/licraft-club/ConfigParser](https://github.com/licraft-club/ConfigParser)

[中文教程](./README-cn.md)
### How to use
```java
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
        //enable debug
        ParserAPI.INSTANCE.setDebug(true);
		//load config
        ParserAPI.INSTANCE.loadConfig(this, config);
    }

    @Override
    public void onDisable() {
	    //save config
        ParserAPI.INSTANCE.saveConfig(this, config);
    }
```
### @ConfigBean
`@ConfigBean` configures which configuration file to load for classes with annotation configuration. We can specify the configuration file path directly with `file` argument (*config.yml* is default value for file):
```java
@ConfigBean
public class TestConfig {
}
```
Also, we can point the configuration file path to the classes programmatically
```java
public class TestConfig {
}

@Override
public void onEnable() {
	ParserAPI.INSTANCE.loadConfig(this,"config.yml",config);
}
```
### @ConfigValue
`@ConfigValue` is an annotation of the field that can be read and written directly through `FileConfiguration`. Such as Primitive Data Types, List and ItemStack.
For example:
```java
/**
* data:
*   name: configParser
*/
@ConfigValue(path = "data.name",colorChar = '&')
public String name;
```
### @ConfigSection
`@ConfigSection` is an annotation of a field that only gets the exact path at runtime. For example, the key-value pairs of player name and points dynamically added at runtime.
```java
/**
*   points:
*     notch: 39
*     md_5: 92
*     dinnerbone: 45
*/
@ConfigValue(path = "data.points")
public Map<String, Integer> pointsMap;
```
You can see the specific usage in the [sample project](https://github.com/licraft-club/ConfigParser).
