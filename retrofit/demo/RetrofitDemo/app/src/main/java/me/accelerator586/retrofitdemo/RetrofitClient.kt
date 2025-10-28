package me.accelerator586.retrofitdemo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Retrofit 客户端单例
 *
 * 学习要点：
 * 1. 使用 Builder 模式创建 Retrofit 实例
 * 2. baseUrl 必须以 / 结尾
 * 3. 可以配置多个 ConverterFactory 和 CallAdapterFactory
 */
object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"

    /**
     * OkHttp 客户端配置
     *
     * 学习要点：
     * - HttpLoggingInterceptor 用于打印请求和响应日志
     * - 可以配置超时时间
     * - 可以添加自定义拦截器
     */
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(createLoggingInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * 创建日志拦截器
     *
     * 调试技巧：
     * - BODY 级别会打印完整的请求和响应
     * - 方便学习 Retrofit 如何构建 HTTP 请求
     */
    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Retrofit 实例
     *
     * 学习要点：
     * 1. baseUrl() - 设置基础 URL
     * 2. client() - 设置 OkHttp 客户端
     * 3. addConverterFactory() - 添加数据转换器（JSON -> 对象）
     *
     * 源码阅读入口：
     * 👉 点击 Retrofit.Builder() 进入源码
     * 👉 查看 build() 方法的实现
     */
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 创建 GitHubService 实例
     *
     * 🔥 核心学习点：
     * 👉 在这里打断点！
     * 👉 Step Into (F7) 进入 create() 方法
     * 👉 观察 Retrofit 如何使用动态代理创建接口实现
     */
    val gitHubService: GitHubService by lazy {
        retrofit.create(GitHubService::class.java)
    }
}