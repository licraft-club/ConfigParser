# ConfigParser

ConfigParser API 是一个用 kotlin 编写的基于注解的配置文件系统, 它允许开发者使用 API 提供的 `saveConfig` and `loadConfig` 方法来读取和保存配置文件.
[源码地址: https://github.com/licraft-club/ConfigParser](https://github.com/licraft-club/ConfigParser)
### 如何使用
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
        //开启 debug
        ParserAPI.INSTANCE.setDebug(true);
		//加载配置文件
        ParserAPI.INSTANCE.loadConfig(this, config);
    }

    @Override
    public void onDisable() {
	    //保存配置文件
        ParserAPI.INSTANCE.saveConfig(this, config);
    }
```
### @ConfigBean
`@ConfigBean` 用来配置 classes 去加载哪个配置文件. 我们可以通过 `file` 参数指定配置文件的具体路径 (*config.yml* 是 file 的默认值):
```java
@ConfigBean
public class TestConfig {
}
```
当然，我们也可以在代码里动态指定配置文件的路径。
```java
public class TestConfig {
}

@Override
public void onEnable() {
	ParserAPI.INSTANCE.loadConfig(this,"config.yml",config);
}
```
### @ConfigValue
`@ConfigValue` 是可以被 `FileConfiguration` 直接读写的字段的注解. 比如 Java 基本类型，List，和 ItemStack.
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
`@ConfigSection` 是只有在运行时才能确定准确路径的字段的注解. 比如在代码里动态添加记录的玩家名字和点数的键值对:
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
你也可以查看更详细的使用方法 [sample project](https://github.com/licraft-club/ConfigParser).