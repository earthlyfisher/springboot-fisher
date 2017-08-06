## 加载时预设值变量问题

### 关于`Environment`的使用

 通过实现`EnvironmentAware`可以获取加载的环境变量,命令行参数以及在`application`文件预设值的变量等.
```java
 
 public class BootEntry implements EnvironmentAware{
 @Override
	public void setEnvironment(Environment environment) {
		System.out.println(environment.getProperty("spring.datasource.password"));
	}
 }
```

 当然以上已经是完全加载后，并初始化所有Bean后，此时已经不能对加载的值中间修改操作。

### 覆盖加载的变量值的几种方法

1. 通过增加`EnvironmentPostProcessor`的实现来实现该功能

```java
public class CustomeEnvPostProcessor implements EnvironmentPostProcessor {
/**
 * ConfigurableEnvironment的异变PropertySource key.
 */
private static final String SOURCE_NAME = "applicationConfigurationProperties";

/**
 * 需要的application变量的key
 */
private static final String ENV_KEY_NAME = "spring.datasource.password";

@SuppressWarnings("unchecked")
@Override
public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
	MutablePropertySources ms = environment.getPropertySources();
	if (ms.contains(SOURCE_NAME)) {
		/*
		 * 1.获取加载application文件的PropertySource
		 */
		PropertySource<?> source = ms.get(SOURCE_NAME);

		if (source.containsProperty(ENV_KEY_NAME)) {
			/*
			 * 2.由于复合关系，
			 * 在这里找出加载application文件的PropertySource下的所有EnumerableCompositePropertySource
			 */
			List<EnumerableCompositePropertySource> lst = (List<EnumerableCompositePropertySource>) source
					.getSource();
			/*
			 * 3.遍历EnumerableCompositePropertySource集合，<br/>
			 * 找出每一个PropertySource的子PropertySource集合,并替换需要的key-value
			 */
			for (EnumerableCompositePropertySource target : lst) {
				if (target.containsProperty(ENV_KEY_NAME)) {
					Collection<PropertySource<?>> sourceSet = target.getSource();
					for (PropertySource<?> propertySource : sourceSet) {
						if (propertySource instanceof MapPropertySource) {
							MapPropertySource targetSource = (MapPropertySource) propertySource;
							addOrReplaceProperty(targetSource);
						}
					}
				}
			}
		}
	}
}

/**
 * 在此做一些对application变量的替换
 * 
 * @param targetSource
 */
private void addOrReplaceProperty(MapPropertySource targetSource) {
	targetSource.getSource().put(ENV_KEY_NAME,
			PasswordUtil.decodePassword(targetSource.getProperty(ENV_KEY_NAME) + ""));
}
}
```
通过追踪源码终于搞定了，好辛苦.
对于上面的实现，需要将其加到`META-INF/spring-factories`文件里,如下:

```java
#Environment Post Processor
org.springframework.boot.env.EnvironmentPostProcessor=\
com.wyp.boot.earthlyfisher.system.EnvPropertiesHandler
```

2. 通过增加`PropertySourceLoader`的实现来实现该功能

由于`Springboot`初始化加载文件是通过实现了`PropertySourceLoader`的class集来完成的，所以可以通过这种方式来实现.我是这么想的，由于`application`文件后缀无非是`yml`或者`properties`,所以完全可以调用这两种文件格式的加载方式来解决,需要做的只是对特定的key做扩展.

```java
public class CustomPropertLoader implements PropertySourceLoader {

	@Override
	public String[] getFileExtensions() {
		return new String[] { "yml", "yaml" };
	}

	@Override
	public PropertySource<?> load(String name, Resource resource, String profile) throws IOException {
		YamlPropertySourceLoader ymlLoader = new YamlPropertySourceLoader();
		PropertySource<?> source = (MapPropertySource) ymlLoader.load(name, resource, profile);
		String propertyKey = "spring.datasource.password";
		if (source instanceof MapPropertySource) {
			MapPropertySource target = (MapPropertySource) source;
			if (target.containsProperty(propertyKey)) {
				String pwd = target.getProperty(propertyKey) + "";
				target.getSource().put(propertyKey, pwd);
			}
		}
		return source;
	 }
     } 
```
 当然此种实现，也需要将其加到`META-INF/spring-factories`文件里,如下:
```
#PropertySourceLoader
org.springframework.boot.env.PropertySourceLoader=\
com.wyp.boot.earthlyfisher.system.CustomPropertyHandler
```
 3. 重写特定的使用实例

  比如`spring.datasource.password`是为了`dataSource`的使用而设置的，完全可以自己定义数据源实例，用来替代`SpringBoot`自动配置为你生成的数据源。只不过这中方式比较复杂，得把需要的属性重新配置一遍，如下:

```java
  @Configuration
 public class CustomDataSource {
	
	@Value(value = "${spring.datasource.driverClass}")
	String driverClassName;
	
	@Value(value = "${spring.datasource.url}")
	String url;
	
	@Value(value = "${spring.datasource.username}")
	String username;
	
	@Value(value = "${spring.datasource.password}")
	String password;

	@Bean
	@Primary
	public DataSource dataSource() {
		DataSource dataSource = new DataSource(); // org.apache.tomcat.jdbc.pool.DataSource;
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}
	}
```
4. 覆盖`PropertyPlaceholderConfigurer`的实现
   此种方式是在普通的Spring项目中自己处理配置文件的一般方式，但是由于`springboot`内部处理的原因，暂时还没有成功，具体的错误原因是，只要我覆盖了原生的实现，则通过自定义注解配置属性时，会找不到，还需看源码以进一步研究.错误信息如下:
   ```java
   Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'spring.datasource.driver-class-name' in string value "${spring.datasource.driver-class-name}"
   at org.springframework.util.PropertyPlaceholderHelper.parseStringValue(PropertyPlaceholderHelper.java:174) ~[spring-core-4.3.4.RELEASE.jar:4.3.4.RELEASE]
   at org.springframework.util.PropertyPlaceholderHelper.replacePlaceholders(PropertyPlaceholderHelper.java:126) ~[spring-core-4.3.4.RELEASE.jar:4.3.4.RELEASE]

   ```

**以上四种方式以第一种方式为佳**,因为其不牵扯到正常的加载逻辑，算是在所有参数加载完后做的一些补充.

## JPA知识点

### `@Temporal`
`@Temporal`是将`java.sql.*`下的`Data`等转成`java.util.*`包下的`Date`.

### 关系维护中的问题

1. `mappedBy`

   `mappedBy`和`inverse`的作用是相同的，只不过`inverse`用在xml文件中，表示的意思是在关系双方都有关系维护任务时，以哪一方为主导，即没有被`mappedBy`修饰的一方维护.即外键将储存在没有被`mappedBy`修饰一方.


## 关于日志的问题

  如果日志配置如下：

```yaml
logging:
   file: D:\\image\\earthlyfihser.log
   path: D:\\image
   level: 
      root: info
```

1. logging.file : 输出到指定的文件，可以为相对路径或者绝对路径
2. logging.path : 与logging.file 是互斥的，指定文件的路径，默认的文件名为 spring.log

日志文件默认达到10Mb 时，将会从新打开一个文件输出，默认的日志输出级别为ERROR，WARN 和 INFO。需要注意的是日志系统的初始化要早于系统的生命周期，因此logging properties 不能够通过@PropertySource 注解获取。

这就是基础的日志配置，可以直接在application.properties配置，我们还可以在classpath路径下，通过定义具体的日志文件来配置.

`Spring Boot` 采用 `Commons Logging` 作为内部的日志框架。
在默认情况下，采用 `Starters` 来启动`Spring Boot` 项目， `Logback` 是默认的日志实现方案。

## 关于idea中devtools不起作用的问题

相信大部分使用Intellij的同学都会遇到这个问题，即使项目使用了spring-boot-devtools，修改了类或者html、js等，idea还是不会自动重启，非要手动去make一下或者重启，
就更没有使用热部署一样。出现这种情况，并不是你的配置问题，相信自己，热部署那几个设置很简单，其根本原因是因为Intellij IEDA和Eclipse不同，Eclipse设置了自动编译之
后，修改类它会自动编译，而IDEA在非RUN或DEBUG情况下才会自动编译（前提是你已经设置了Auto-Compile）。

下面看步骤：
1. 设置自动编译：
  `setting->compiler`下将`build project automa...`勾选
2. `Shift+Ctrl+Alt+/`，选择`Registry`,
   将`compiler.automake.allow.when.app.running`和`actionSystem.assertFocusAccessFromEdt`勾选.
   
## 多数据源配置：

>[Spring Boot多数据源配置与使用](http://www.jianshu.com/p/34730e595a8c)

## 关于打成war

在一般的项目中，需要将项目打包成war包供发布到tomcat,所以自己实现了下：
1. `extends SpringBootServletInitializer`,重写`configure`方法.
   ```java
    @SpringBootApplication
    public class BootEntry4War extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BootEntry4War.class);
    }

    /**
     * 1.If you need to find out what auto-configuration is currently being
     * applied, and why, start your application with the --debug switch .<br/>
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootEntry4War.class);
        application.run(args);
    }

    }
   ```
2. 更改pom文件打包形式为`war`
3. 为了确保内嵌的servlet容器不能干扰war包将部署的servlet容器。为了达到这个目的，你需要将内嵌容器的依赖标记为provided。

## `Spring MVC`转换`JPA`多对多对象的`json`时，无限循环问题

在使用JPA处理多对多关系时，发生了无限循环的问题，代码如下：
```java
　　@Entity
   @Table(name = "t_menu")
   public class Menu implements Comparable<Menu> {
       @Id
       @GeneratedValue(strategy = GenerationType.AUTO)
       private long id;
       private String menuName;
       private String menuDesc;
       private int priority;
       private String staticIndex;
       private int parantId;
       private boolean enabled;
       @Transient
       private List<Menu> children;
   
       //菜单所属role
       @ManyToMany(mappedBy = "roleMenus", fetch = FetchType.LAZY)
       private Set<Role> roles = new HashSet<>();
   
       //菜单所属role
       @ManyToMany(mappedBy = "userMenus", fetch = FetchType.LAZY)
       private Set<User> users = new HashSet<>();
       }
```
上面转换成json数据时，出现无限循环以致栈溢出．
针对以上问题可以通过以下注解实现：
1. @JsonIgnore json转换时忽略某个属性，以断开无限递归，序列化和反序列化均忽略，可以用在字段或get(序列化),set(反序列化)方法上
2. @JsonBackReference json转换时忽略某个属性，以断开无限递归，序列化时忽略，可以用在字段或get(序列化),set(反序列化)方法上，序列化时,相当于@JsonIgnore
3. @JsonManagedReference json转换时会被序列化，反序列化时，如果没有该注解，则不会自动注入@JsonBackReference标注的属性

使用如下：
```java
   //菜单所属role
    @JsonIgnore
    @ManyToMany(mappedBy = "roleMenus", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    //菜单所属role
    @JsonBackReference
    @ManyToMany(mappedBy = "userMenus", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
```

##`@Bean`　in　`@Configuration`　vs　`@Component`

`@Bean`可以出现在`@Configuration`or`@Component`，其中`@Configuration`类似于xml中的`<beans>`,而`@Component`类似于xml中的`<bean>`,
`@Component`可以作为`@Configuration`的替代。但是有一些问题,如下：
>当我们使用`@Bean`注解在例如`@Component`作用的class里面时，将会发生一种称之为注解`@Bean`的`lite mode`出现，这种不会使用`CGLIB`代理.所以只要
我们在`@Bean`修饰的方法之间不相互编码调用，代码将会很好的运作.

**`@Bean`的`lite mode`**
```java
    @Component
    public class ConfigInComponent {
    
        /**
        *1
        **/
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    
        @Bean
        public SimpleBeanConsumer simpleBeanConsumer() {
            return new SimpleBeanConsumer(simpleBean());
        }
    }
```

*上述代码在`new SimpleBeanConsumer(simpleBean())`这一步实例化`bean`时，不会将第一步`@Bean`实例化的bean自动注入到`simpleBeanConsumer`
bean中，而是重新调用`simpleBean()`，生成一个新的`SimpleBean` 实例*.而`@Configuration`则不会发生上述情况,代码如下：

```java
    @Configuration
    public class ConfigInConfiguration {
    
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    
        @Bean
        public SimpleBeanConsumer simpleBeanConsumer() {
            return new SimpleBeanConsumer(simpleBean());
        }
    }
```

要改善上述问题，可以通过以下方式实现：

```java
    @Component
    public class ConfigInComponent {
    
        @Autowired
        SimpleBean simpleBean;
    
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    
        @Bean
        public SimpleBeanConsumer simpleBeanConsumer() {
            return new SimpleBeanConsumer(simpleBean);
        }
    }
```

通过将`@Bean`生成的bean Autowired到属性上，并在`@Bean`实例化`SimpleBeanConsumer`bean时传入此属性，来达到目的.

**参考**

[Spring @Configuration vs @Component](http://dimafeng.org/2015/08/29/spring-configuration_vs_component)
 

##`@Named`,`@Inject`

`@Inject` and `@Named` are JSR 330 Standard Annotations.
* `@Autowired` == `@javax.inject.Inject`
* `@Component` == `@javax.inject.Named`

##http header中`accept`vs`content-type`



