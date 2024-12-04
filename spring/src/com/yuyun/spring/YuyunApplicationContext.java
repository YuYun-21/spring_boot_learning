package com.yuyun.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hyh
 * @since 2024-12-03
 */
public class YuyunApplicationContext {
    /**
     * config类
     */
    private Class configClass;
    /**
     * Bean定义Map key:beanName value:BeanDefinition
     */
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap();
    /**
     * 单例池 Map key:beanName value:Object
     */
    private ConcurrentHashMap<String, Object> singletonObjectMap = new ConcurrentHashMap();

    public YuyunApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描 ---> BeanDefinition ---> beanDefinitionMap
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            // 拿到注解
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            // 注解内的扫描路径 com.yuyun.service
            String path = componentScanAnnotation.value();
            // com/yuyun/service
            path = path.replace(".", "/");

            ClassLoader classLoader = YuyunApplicationContext.class.getClassLoader();
            // 类加载器相对路径 获取资源 com/yuyun/service下的文件或目录
            URL resource = classLoader.getResource(path);
            // File可以表示文件夹也可以表示文件
            File file = new File(resource.getFile());

            // 是目录
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    // 获取绝对路径
                    String fileName = f.getAbsolutePath();

                    // 判断是不是类 因为文件很多
                    if (fileName.endsWith(".class")) {
                        // 拿到类名 com/yuyun/service/UserService 去掉绝对路径前面的部分和后面的.class
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));

                        // com.yuyun.service.UserService
                        className = className.replace("/", ".");
                        // className = className.replace("\\", ".");

                        try {
                            // 类加载器根据类名加载类 com.yuyun.service.UserService
                            Class<?> clazz = classLoader.loadClass(className);
                            // 判断是否加了Component注解
                            if (clazz.isAnnotationPresent(Component.class)) {
                                // 拿到注解
                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                // 拿到注解的值作为bean的名称
                                String beanName = componentAnnotation.value();

                                if ("".equals(beanName)) {
                                    // 首字母大写改为小写 第1个字母和第2个字母大写时不管
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }

                                // 类上有Component注解，定义了这个类为Bean，创建BeanDefinition对象 单例Bean直接创建，多例Bean不创建
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);

                                // 判断是单例还是多例 没加Scope注解为单例
                                beanDefinition.setScope(clazz.isAnnotationPresent(Scope.class) ? clazz.getAnnotation(Scope.class).value() : "singleton");

                                // 放入Bean定义池
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        // 实例化单例Bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                // 单例
                Object bean = createBean(beanName, beanDefinition);
                // 放入单例池
                singletonObjectMap.put(beanName, bean);
            }
        }
    }

    /**
     * 创建bean
     *
     * @param beanName       bean名称
     * @param beanDefinition bean定义
     * @return {@code Object }
     */
    private Object createBean(String beanName, BeanDefinition beanDefinition) {

        // 获取bean的class
        Class clazz = beanDefinition.getType();
        try {
            // 无参构造器 创建bean
            Object instance = clazz.getConstructor().newInstance();

            return instance;
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取bean
     *
     * @param beanName bean名称
     * @return {@code Object }
     */
    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        }
        String scope = beanDefinition.getScope();
        if (scope.equals("singleton")) {
            // 单例 直接从单例池里面拿
            Object bean = singletonObjectMap.get(beanName);
            if (bean == null) {
                // 拿不到 重新创建
                bean = createBean(beanName, beanDefinition);
                // 放入单例池
                singletonObjectMap.put(beanName, bean);
            }
            return bean;
        } else {
            // 多例 每次都要创建
            return createBean(beanName, beanDefinition);
        }
    }
}
