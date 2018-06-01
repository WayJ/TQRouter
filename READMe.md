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

宿主中初始化
```java

    //放到host的Application的OnCreate方法
    @Override
	public void onCreate() {
		super.onCreate();
	    TQRouter.setRouterLauncher(new RePluginLauncher());
	    TQRouter.setUp(this);
	}

	class RePluginLauncher extends BundleLauncher{

        @Override
        public void launchBundle(Bundle bundle, Context context, Postcard postcard) {
            String mainPluginName=bundle.getPackageName();
            //检测插件是否安装
            if (RePlugin.isPluginInstalled(mainPluginName)) {
                Intent intent = RePlugin.createIntent(mainPluginName, postcard.getPath());
                if(TextUtils.isEmpty(postcard.getQuery())){
					intent.putExtra(TQRouter.KEY_QUERY, '?'+postcard.getQuery());
				}
                //启动插件
                RePlugin.startActivity(context, intent);
            }else{
                return;
            }
        }

	}

```

插件主动注册自己

```java
    TQRouter.register(this);
```

调用路由跳转activity

```java
    TQRouter.openUri("main/login?userName=admin&password=123456")
```

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
      "pkg": "com.tianque.cmm.app.mine"
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
