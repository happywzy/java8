package com.deri.stream.plugin;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: PluginXmlParser
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/9/29 10:56
 * @Version: v1.0
 **/
public class PluginXmlParser {

    public static List<Plugin> getPluginList() throws PluginException {

        List<Plugin> list = new ArrayList<>();

        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(new File("plugin.xml"));
        } catch (Exception e) {
            throw new PluginException("read plugin.xml error," + e.getMessage());
        }
        Element root = document.getRootElement();
        List<?> plugins = root.elements("plugin");
        for (Object pluginObj : plugins) {
            Element pluginEle = (Element) pluginObj;
            Plugin plugin = new Plugin();
            plugin.setPluginName(pluginEle.elementText("name"));
            plugin.setJarPath(pluginEle.elementText("jar"));
            plugin.setClassName(pluginEle.elementText("class"));
            list.add(plugin);
        }
        return list;
    }

}
