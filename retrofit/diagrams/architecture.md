
**创建以下图表**（我给你文字描述，你可以用 draw.io 画）：

#### 📊 `architecture.png` - 整体架构
```
┌─────────────────────────────────────────────┐
│          用户代码（MainActivity）              │
└─────────────────┬───────────────────────────┘
                  │ 调用接口方法
                  ↓
┌─────────────────────────────────────────────┐
│      GitHubService（接口定义）                 │
│  @GET("users/{user}/repos")                 │
│  suspend fun listRepos(...)                 │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│        Retrofit.create() 动态代理             │
│  Proxy.newProxyInstance()                   │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│      InvocationHandler.invoke()             │
│  拦截所有方法调用                               │
└─────────────────┬───────────────────────────┘
                  │
                  ↓
┌─────────────────────────────────────────────┐
│         ServiceMethod                       │
│  解析注解、缓存方法元数据                        │
└─────────────────┬───────────────────────────┘
                  │
        ┌─────────┴─────────┐
        ↓                   ↓
┌──────────────┐    ┌──────────────┐
│ CallAdapter  │    │  Converter   │
│ 适配返回类型   │    │  数据转换     │
└──────┬───────┘    └──────┬───────┘
       │                   │
       └─────────┬─────────┘
                 ↓
        ┌────────────────┐
        │   OkHttpCall   │
        │  构建 Request   │
        └────────┬───────┘
                 ↓
        ┌────────────────┐
        │     OkHttp     │
        │  执行网络请求    │
        └────────┬───────┘
                 ↓
        ┌────────────────┐
        │    Response    │
        │   返回给用户     │
        └────────────────┘
```

#### 🔄 `create-flow.png` - create() 流程
```
retrofit.create(GitHubService::class.java)
         ↓
validateServiceInterface(service)
验证接口合法性
         ↓
Proxy.newProxyInstance(
    classLoader,
    interfaces,
    InvocationHandler { proxy, method, args ->
        loadServiceMethod(method).invoke(args)
    }
)
         ↓
返回代理对象
```

#### 📞 `request-flow.png` - 请求执行流程

```
service.listRepos("square")
         ↓
InvocationHandler.invoke()
         ↓
loadServiceMethod(method)
    ├─ 检查缓存
    │  └─ 有缓存 → 直接返回
    └─ 无缓存 → 解析
         ↓
ServiceMethod.parseAnnotations()
    ├─ 解析方法注解 (@GET)
    ├─ 解析参数注解 (@Path)
    └─ 创建 ServiceMethod
         ↓
缓存 ServiceMethod
         ↓
ServiceMethod.invoke(args)
         ↓
CallAdapter.adapt()
         ↓
创建 OkHttpCall
         ↓
OkHttp.execute() / enqueue()
         ↓
Converter.convert()
         ↓
返回结果
```

### 8.3 保存图片

将画好的图保存为 PNG 格式，放入 `retrofit/diagrams/` 目录：
```
retrofit/diagrams/
├── architecture.png
├── create-flow.png
├── request-flow.png
└── source.drawio        # 👈 保存可编辑的源文件
