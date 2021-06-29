## sonar自定义规则插件开发
在 [sonarqube_java_rule_custom](https://github.com/wychenzhou/sonarqube_java_custom_rule) 进行扩展，基于MIT License自定义开发

## 开发环境
- jdk 1.8
- maven

## 使用说明
1. git clone  https://github.com/GuoGuang/sonarqube_java_rule_custom.git
2. mvn -DskipTests=true install
3. 将打包好的jar放到extensions/plugins 目录下之后重启

## 开发指南
- [自定义规则开发指南](https://docs.sonarqube.org/display/PLUG/Custom+Rules+for+Java)

- [官方示例](https://github.com/SonarSource/sonar-custom-rules-examples/tree/master/java-custom-rules)

## 已完成的规则

### 命名
- 抽象类命名使用 Abstract 或 Base 开头 
- 异常类命名使用Exception结尾
- 方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase，必须遵从驼峰形式 
- 常量命名应该全部大写，单词间用下划线隔开，力求语义表达完整清楚  
- 所有编程相关的命名均不能以下划线或美元符号开始    
- POJO 类中布尔类型的变量，都不要加 is 前缀  
- 包命名检查:包名统一使用小写，点分隔符之间有且仅有一个自然语义的英语单词  
- 测试类命名检查:测试类命名以它要测试的类名开始，以 Test 结尾  

### 注释
- 所有的类都必须添加创建者信息   提示
- 类、类属性、类方法的注释必须存在且使用javadoc规范，使用/**内容*/格式，不得使用//xxx方式和/*xxx*/方式
- 所有的枚举类型字段必须要有注释，说明每个数据项的用途 

### 多线程
- 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式,
    规避资源耗尽的风险
- 线程资源必须通过线程池提供，不允许在应用中自行显式创建线程

### 异常
- 不能在finally块中使用return,finally块中的return返回后方法结束执行,
    不会再执行try块中的return语句 
- 返回类型为基本数据类型，return包装数据类型的对象时，自动拆箱有可能产生NPE

### 流程控制
- 除常用方法（如getXxx/isXxx）等外，不要在条件判断中执行复杂的语句，将复杂逻辑判断的结果赋值给一个有意义的布尔变量，以提高可读性
- 在if/else/for/while/do语句中,代码块应该写在花括号里,否则可能会影响代码执行 
- 在一个switch块内，都必须包含一个default语句并且放在最后，即使它什么代码也没有
- 在一个switch块内，每个case通过break/return等来终止,要么通过注释来说明

### 面向对象
- 不能使用过时的类或方法 
- 所有的包装类对象之间值的比较，全部使用equals方法比较
- 获取当前毫秒数：System.currentTimeMillis(); 而不是new Date().getTime()

### 集合
- 集合初始化时，指定集合初始值大小
- ArrayList的subList结果不可强转成ArrayList，否则会抛出ClassCastException异常

### 其他
- 避免用Apache Beanutils进行属性的copy
- 正确使用正则表达式,利用好其预编译功能,可以有效加快正则匹配速度 
- 所有的抽象方法（包括接口中的方法）必须要用javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法做什么事情，实现什么功能 
- Object的equals方法容易抛空指针异常，应使用常量或确定有值的对象来调用equals
- 函数代码块不可超过50行

## 扩展：SonarQube常用api
- nodesToVisit：指定要扫描的节点 (也就是树的分支)
- visitNode：获取到 nodesToVisit 中的过滤后的所有节点 Tree，并且按照用户指定的逻辑进行校验。
    ```java
    public void visitNode(Tree tree) {
    //获取该节点开始行，例如某方法的第一行行号
    System.out.println(tree.firstToken().line());
    //获取该节点结束行，例如某方法的最后一行行号
    System.out.println(tree.lastToken().line());
    }
    ```
  - resources 文件夹下的xxx.json中的constantCost：代码味道的修复成本评估 