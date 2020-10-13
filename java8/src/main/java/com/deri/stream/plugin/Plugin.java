package com.deri.stream.plugin;

import lombok.Data;

/**
 * @ClassName: Plugin
 * @Description: TODO
 * @Author: wuzhiyong
 * @Time: 2020/9/29 10:02
 * @Version: v1.0
 **/
@Data
public class Plugin {
    /**
     * 插件名称
     */
    private String pluginName;
    /**
     * jar包完整路径
     */
    private String jarPath;
    /**
     * class完整路径
     */
    private String className;
}
