package com.deri.stream.plugin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PluginManager
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/9/29 10:35
 * @Version: v1.0
 **/
@Data
@NoArgsConstructor
public class PluginManager {

    private Map<String, Class> clazzMap = new HashMap<>();

    public PluginManager(List<Plugin> plugins) throws PluginException {
        initPlugins(plugins);
    }

    public void initPlugin(Plugin plugin) throws PluginException {
        try {
//            URL url = new URL("file:" + plugin.getJarPath());
            URL url = new File(plugin.getJarPath()).toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            Class clazz = classLoader.loadClass(plugin.getClassName());
            clazzMap.put(plugin.getClassName(), clazz);
        } catch (Exception e) {
            throw new PluginException("plugin " + plugin.getPluginName() + " init error," + e.getMessage());
        }
    }

    public void initPlugins(List<Plugin> plugins) throws PluginException {
        for (Plugin plugin : plugins) {
            initPlugin(plugin);
        }
    }

    public PluginService getInstance(String className) throws PluginException {
        Class clazz = clazzMap.get(className);
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            throw new PluginException("plugin " + className + " instantiate error," + e.getMessage());
        }
        return (PluginService) instance;
    }
}
