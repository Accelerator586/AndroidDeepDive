package me.accelerator586.retrofitdemo

/**
 * UI 状态封装
 *
 * Compose 最佳实践：
 * - 使用 sealed class 表示不同的 UI 状态
 * - 让 UI 根据状态自动更新
 */
sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val repos: List<Repo>, val method: String) : UiState()
    data class Error(val message: String) : UiState()
}