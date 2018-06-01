# TQRouter 路由组件

------

该组件的开发目的：

> * 组件化、插件化后的组件使用问题
> * 多类型uri使用，兼容weex的uri和web端uri
> * 兼容Replugin
> * 简单方便的功能，调用简单，扩展方便，编译快

------

## 支持Replugin的方式

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
  
------

## ToDoList

- [ ] 支持跨进程
- [ ] 子插件主动向宿主注册自己的路由配置（待测试）
- [ ] 下发新插件时候，宿主自动读取插件路由配置信息
- [ ] 使用路由跳转时，添加uri参数，如 “main/login?userName=admin&password=123456”
- [ ] 通过路由创建fragment
- [ ] 通过路由启动Service
- [ ] 其他更多功能
