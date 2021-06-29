package com.madaoo.sonarqube;

import org.sonar.api.Plugin;

/**
 * @author chenzhou
 * <p>
 * <p>
 * 插件的入口点包含的自定义规则
 * 需要在 pom.xml中配置声明
 */
public class MyJavaRulesPlugin implements Plugin {

    @Override
    public void define(Context context) {

        // 服务器扩展 - >对象在服务器启动期间被实例化
        context.addExtension(MyJavaRulesDefinition.class);

        // 批量扩展 - >代码分析过程中实例化对象
        context.addExtension(MyJavaFileCheckRegistrar.class);
    }
}
