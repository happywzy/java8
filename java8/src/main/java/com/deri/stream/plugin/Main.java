package com.deri.stream.plugin;

import java.util.List;

/**
 * @ClassName: Main
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/9/29 11:01
 * @Version: v1.0
 **/
public class Main {
    public static void main(String[] args) throws PluginException {
        List<Plugin> pluginList = PluginXmlParser.getPluginList();
        PluginManager pluginManager = new PluginManager(pluginList);

        for (Plugin plugin : pluginList) {
            PluginService pluginService = pluginManager.getInstance(plugin.getClassName());
            System.out.println("开始执行[" + plugin.getPluginName() + "]插件...");
            // 调用插件
            pluginService.process();
            System.out.println("[" + plugin.getPluginName() + "]插件执行完成");
        }

        Plugin plugin = new Plugin();
        plugin.setPluginName("B插件");
        plugin.setJarPath("D:\\flinkDemo\\java8\\out\\artifacts\\test\\test.jar");
        plugin.setClassName("com.deri.stream.plugina.PluginB");
        pluginManager.initPlugin(plugin);
        PluginService pluginService = pluginManager.getInstance("com.deri.stream.plugina.PluginB");
        pluginService.process();
    }
}
