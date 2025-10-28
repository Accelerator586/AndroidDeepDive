# Retrofit 整体概览

> 学习日期：2025-10-28
> 学习进度：✅ 完成

## 📋 基本信息

| 项目 | 内容 |
|------|------|
| 名称 | Retrofit |
| 版本 | 2.9.0 |
| 开发者 | Square |
| 开源协议 | Apache 2.0 |
| GitHub | https://github.com/square/retrofit |
| 官网 | https://square.github.io/retrofit/ |
| 代码行数 | ~3000 行（核心代码） |

## 🎯 Retrofit 是什么？

Retrofit 是一个**类型安全的 HTTP 客户端**，专为 Android 和 Java 设计。它通过**接口定义 API**，使用**注解描述请求**，让网络请求变得优雅而简洁。

### 为什么叫 Retrofit？

> "Retrofit" = Re（重新）+ Fit（适配）

意思是将 HTTP API "重新适配"成 Java/Kotlin 接口，让开发者可以像调用普通方法一样发起网络请求。

## 🌟 核心特性

### 1. 声明式 API 定义
```kotlin
interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}
```

**优势**：
- ✅ 清晰：API 定义就是文档
- ✅ 类型安全：编译期检查参数和返回值
- ✅ 简洁：不需要写大量的 HTTP 调用代码

### 2. 动态代理生成实现
```kotlin
val service = retrofit.create(GitHubService::class.java)
// 👆 Retrofit 运行时动态生成接口的实现类
```

### 3. 灵活的扩展机制

- **Converter**：支持多种数据格式（JSON, XML, Protobuf 等）
- **CallAdapter**：支持多种异步模式（Coroutine, RxJava, LiveData 等）

### 4. 基于 OkHttp

Retrofit 底层使用 OkHttp 执行实际的网络请求，享受 OkHttp 的所有优势：
- HTTP/2 支持
- 连接池
- GZIP 压缩
- 响应缓存

## 🏗️ 整体架构
```
用户代码
    ↓
接口定义（GitHubService）
    ↓
Retrofit.create() - 动态代理
    ↓
InvocationHandler.invoke() - 拦截方法调用
    ↓
ServiceMethod - 解析注解
    ↓
CallAdapter - 适配返回类型
    ↓
OkHttpCall - 构建请求
    ↓
OkHttp - 执行网络请求
    ↓
Converter - 转换响应
    ↓
返回给用户
```

**详细架构图**：见 [../diagrams/architecture.png](../diagrams/architecture.png)

## 📦 核心类说明

### 1. Retrofit 类

**作用**：核心类，负责创建 API 接口的实现

**关键方法**：
- `create(Class<T> service)` - 创建接口实现（动态代理）
- `Builder` - 建造者模式配置 Retrofit

**源码位置**：`retrofit2/Retrofit.java`

---

### 2. ServiceMethod 类

**作用**：封装接口方法的元数据（注解、参数等）

**关键方法**：
- `parseAnnotations()` - 解析方法注解
- `invoke()` - 执行方法调用

**源码位置**：`retrofit2/ServiceMethod.java`

---

### 3. HttpServiceMethod 类

**作用**：ServiceMethod 的 HTTP 实现

**关键方法**：
- `parseAnnotations()` - 解析 HTTP 注解
- `invoke()` - 创建 OkHttpCall

**源码位置**：`retrofit2/HttpServiceMethod.java`

---

### 4. RequestFactory 类

**作用**：根据注解构建 OkHttp Request

**关键方法**：
- `parseAnnotations()` - 解析方法和参数注解
- `create()` - 创建 Request 对象

**源码位置**：`retrofit2/RequestFactory.java`

---

### 5. CallAdapter 接口

**作用**：适配不同的返回类型（Call, Observable, Deferred 等）

**关键方法**：
- `adapt(Call<R> call)` - 转换 Call 对象

**源码位置**：`retrofit2/CallAdapter.java`

---

### 6. Converter 接口

**作用**：数据格式转换（JSON ↔ 对象）

**关键方法**：
- `convert()` - 执行转换

**源码位置**：`retrofit2/Converter.java`

---

## 🎨 设计模式

Retrofit 应用了多种设计模式，展现了优秀的架构设计：

### 1. 动态代理模式 ⭐⭐⭐⭐⭐

**使用场景**：`Retrofit.create()` 动态生成接口实现

**优势**：
- 无需手动编写实现类
- 灵活，可运行时修改行为
- 代码简洁

**详细分析**：[02-dynamic-proxy.md](./02-dynamic-proxy.md)

---

### 2. 建造者模式（Builder Pattern）

**使用场景**：`Retrofit.Builder` 配置 Retrofit 实例
```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

**优势**：
- 配置清晰
- 参数可选
- 链式调用

---

### 3. 工厂模式（Factory Pattern）

**使用场景**：
- `CallAdapter.Factory` - 创建 CallAdapter
- `Converter.Factory` - 创建 Converter

**优势**：
- 扩展性好
- 解耦
- 支持多种实现

---

### 4. 适配器模式（Adapter Pattern）

**使用场景**：`CallAdapter` 适配不同的返回类型

**优势**：
- 支持多种异步框架
- 不侵入核心代码
- 易于扩展

---

### 5. 策略模式（Strategy Pattern）

**使用场景**：`Converter` 选择不同的转换策略

**优势**：
- 运行时选择策略
- 易于切换

---

## 🔍 使用流程（以 Demo 为例）

### 1. 定义接口
```kotlin
interface GitHubService {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}
```

### 2. 创建 Retrofit 实例
```kotlin
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

### 3. 创建接口实现
```kotlin
val service = retrofit.create(GitHubService::class.java)
```

**背后发生了什么？**
- Retrofit 使用 `Proxy.newProxyInstance()` 创建动态代理
- 返回一个实现了 `GitHubService` 接口的对象
- 实际上没有生成 `.class` 文件，完全在运行时完成

### 4. 调用方法
```kotlin
val repos = service.listRepos("square")
```

**背后发生了什么？**
1. InvocationHandler 拦截方法调用
2. 解析 `@GET` 和 `@Path` 注解
3. 构建 HTTP GET 请求：`GET https://api.github.com/users/square/repos`
4. 使用 OkHttp 执行请求
5. 用 Gson 将 JSON 转换为 `List<Repo>`
6. 返回结果

---

## 📊 与其他网络库对比

| 特性 | Retrofit | OkHttp | Volley | HttpURLConnection |
|------|----------|--------|--------|-------------------|
| 易用性 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| 类型安全 | ✅ | ❌ | ❌ | ❌ |
| 扩展性 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| 性能 | ⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |
| 社区支持 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐ |

**结论**：Retrofit = 易用性 + OkHttp 的性能 + 类型安全

---

## 🎯 学习目标

通过学习 Retrofit 源码，我希望掌握：

- [x] 动态代理的实际应用
- [x] 注解驱动的 API 设计
- [ ] 建造者模式的最佳实践
- [ ] 工厂模式的扩展机制
- [ ] 拦截器链的设计
- [ ] 线程模型和异步处理
- [ ] 如何设计一个优秀的开源库

---

## 📚 学习资源

### 官方资源
- [Retrofit 官网](https://square.github.io/retrofit/)
- [Retrofit GitHub](https://github.com/square/retrofit)
- [官方文档](https://square.github.io/retrofit/)

### 推荐文章
- [Retrofit 源码分析](https://www.jianshu.com/p/308f3c54abdd)
- [深入理解 Retrofit](https://juejin.cn/post/6844903615455633415)

### 相关项目
- [OkHttp](https://github.com/square/okhttp) - Retrofit 的底层实现
- [Gson](https://github.com/google/gson) - JSON 解析库
- [Moshi](https://github.com/square/moshi) - 另一个 JSON 库

---

## 🔜 下一步

阅读 [02-dynamic-proxy.md](./02-dynamic-proxy.md) - 深入理解动态代理机制

---

**返回**: [Retrofit 学习主页](../README.md) | [AndroidDeepDive 主页](../../README.md)
