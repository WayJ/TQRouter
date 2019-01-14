# TQRouter 路由组件

------

该组件的开发目的和实现思路：

> * 兼容Replugin插件化后的Android组件调用问题
> * 多类型uri的支持
> * 简单方便的功能，调用简单，扩展方便
> * 无注解，无反射，无Gradle依赖
> * 路由配置文件在一个存放assets下的json文件中-
> * 路由配置文件可通过网络下发进行更新
> * 路由配置支持不同个体插件自己注册自己的路由
> * 代码的实现参考了[small](https://github.com/wequick/Small)

------

## 初始化、使用

依赖

```

	
    implementation 'com.wayj.tqrouter:tqrouter:0.1.3'
    implementation 'com.wayj.tqrouter:adapter-replugin:0.1.3'
    
    //implementation project(':lib.router')
    //replugin 中使用请依赖router.adapter-replugin
    //implementation project(':lib.router.adapter-replugin')
```

宿主中初始化

```java
    //放到host的Application的OnCreate方法
    @Override
	public void onCreate() {
		super.onCreate();
        //Replugin 使用：要在setUp前先注册RepluginActivityLauncher
	    TQRouter.setRouterLauncher(new RepluginActivityLauncher());
        //普通项目中
	    TQRouter.setRouterLauncher(new ActivityBundleLauncher());
	    TQRouter.setUp(this);
	    
	    //AppLCObserver 调用
	    TQRouter.getAppLCOCaller().callOnCreate(this);
	}
	
	@Override
    public void onTerminate() {
        super.onTerminate();
        TQRouter.getAppLCOCaller().callOnStop();//可以做一些结束操作
    }

```

AppLCObserver 用来在你的模块中能够调用到host的application的生命周期
默认查找该类的classname为 module包名下的AppObserver类，暂无设置功能，例如"com.wayj.example.app.main.AppObserver"
```
public interface AppLCObserver {
    /**
     * 这里必须返回一个tag，可以是模块名，也可以是具体的功能，比如单独初始化bugly的类，tag应设置为bugly。会用这个tag做重复筛选和异常控制
     * @return
     */
    String getTag();

    /**
     * 可以通过AppLCOCaller.addLCObserver 来在一个模块中添加多个AppLCObserver
     * 建议做法，每一个复杂的组件的初始化单独放一个AppLCObserver，通过tag来区分
     * 例如：BuglyAppObserver,BaiduMapAppObserver。然后通过onSetup方法加入到AppLCOCaller中
     * 对于tag相同的Observer，仅加载最后一个
     * @param lcoCaller
     */
    void onSetup(AppLCOCaller lcoCaller);
    void onCreate(Application application);
    void onStop();
}
```

调用路由跳转activity

```java
    TQRouter.openUri("main/login?userName=admin&password=123456")
```

调用当前插件内部的Fragment

```java
	TQRouter.createFragment("main/fragmentA",context);
	TQRouter.createFragmentV4("main/fragmentA",context);
	TQRouter.createObject("fragment","main/fragmentA",context);  
	TQRouter.createObject("fragment-v4","main/fragmentA",context); 
```

带result的activity跳转可用getIntentOfUri获得Intent对象

```
TQRouter.getIntentOfUri
```

(开发中)通过如下API来调用带result的activity跳转</p> 
```
TQRouter.openUriWithResult(FragmentManager,OnPostResultListener)
```
通过一个中转Fragment（[采用方案](https://blog.csdn.net/gengqiquan/article/details/74331845)）</p> 



JSON路由配置

请将router.json配置文件存放到 宿主的assets文件下，插件也可以放在自己的assets下

```json
{
  "version": "1.0.0",
  "bundles": [
    {
      "uri": "main",
      "pkg": "com.tianque.cmm.app.main",
      "rules": {
        "": ".enter.LaunchActivity"
      }
    },
    {
      "uri": "home",
      "pkg": "com.tianque.cmm.app.home"
    },
    {
      "uri": "mine",
      "pkg": "com.tianque.cmm.app.mine",
        "rules":{
            "mainFragment":".MainFragment"
        }
    }
  ]
}

```

默认openUri("main")即跳转到对应的pkg-“wayj.example”+".MainActivity",

rules下的如果key为“”或者“/”即表示当前Bundle的默认路径  

------

## 源码说明

### Bundle

Bundle是具体的一个module，业务模块之类，并非对应插件，一个插件可能包含了多个module，所以这里Bundle是对应module

### Postcard

Postcard为单次的调用请求的实体，包含了uri、解析后的path、请求参数、bundle等对象

### Bundle Launcher

为请求处理对象，处理postcard的具体请求，将这个抽象出来的原因是为了让该框架支持更多的不同的跳转功能，例如非replugin下组件化架构中使用，例如未来要支持的其他类型（web，weex等）uri。

Bundle Launcher中的launchBundle方法为当前需要实现的主要方法。

------

## ToDoList

- [ ] 支持跨进程
- [ ] 子插件主动向宿主注册自己的路由配置（待测试）
- [ ] 下发新插件时候，宿主自动读取插件路由配置信息
- [ ] 使用路由跳转时，添加uri参数，如 “main/login?userName=admin&password=123456”
- [ ] 通过路由创建fragment
- [ ] 通过路由启动Service
- [ ] 其他更多功能
