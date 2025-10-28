package me.accelerator586.retrofitdemo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * GitHub API 接口定义
 *
 * 学习要点：
 * 1. 接口定义了 API 的契约
 * 2. 注解描述了 HTTP 方法和路径
 * 3. 返回类型是 Call<T>，T 是响应体类型
 */
interface GitHubService {
    /**
     * 获取用户的仓库列表
     *
     * @GET 注解：
     * - 声明这是一个 GET 请求
     * - 参数是相对路径（相对于 baseUrl）
     *
     * @Path 注解：
     * - 将方法参数替换到 URL 的 {user} 位置
     * - 例如：user="square" -> "users/square/repos"
     */
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Repo>>

    /**
     * 协程版本（推荐）
     *
     * 学习要点：
     * - 使用 suspend 关键字
     * - 返回类型直接是 List<Repo>，不需要 Call 包装
     * - Retrofit 会自动处理协程
     */
    @GET("users/{user}/repos")
    suspend fun listReposAsync(@Path("user") user: String): List<Repo>
}