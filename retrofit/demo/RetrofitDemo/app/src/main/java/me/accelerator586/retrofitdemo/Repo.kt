package me.accelerator586.retrofitdemo

import com.google.gson.annotations.SerializedName

/**
 * 仓库数据模型
 *
 * 学习要点：
 * 1. 使用 @SerializedName 指定 JSON 字段名
 * 2. Kotlin 代码使用驼峰命名（camelCase）
 * 3. 这样既符合 Kotlin 规范，又能正确映射 JSON
 */
data class Repo(
    val id: Long,
    val name: String,

    // JSON: "full_name" -> Kotlin: fullName
    @SerializedName("full_name")
    val fullName: String,

    val description: String?,

    // JSON: "html_url" -> Kotlin: htmlUrl
    @SerializedName("html_url")
    val htmlUrl: String,

    // JSON: "stargazers_count" -> Kotlin: stargazersCount
    @SerializedName("stargazers_count")
    val stargazersCount: Int,

    val language: String?,
    val owner: Owner
)

/**
 * 仓库所有者信息
 */
data class Owner(
    val login: String,

    // JSON: "avatar_url" -> Kotlin: avatarUrl
    @SerializedName("avatar_url")
    val avatarUrl: String
)