package me.accelerator586.retrofitdemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import me.accelerator586.retrofitdemo.ui.theme.RetrofitDemoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RetrofitDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RetrofitDemoScreen()
                }
            }
        }
    }
    @Composable
    fun RetrofitDemoScreen() {
        var uiState by remember { mutableStateOf<UiState>(UiState.Idle) }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 标题
            Text(
                text = "Retrofit 源码学习 Demo",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // 方式1：Callback 风格
            Button(
                onClick = {
                    fetchReposWithCallback("square") { state ->
                        uiState = state
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("方式1: Callback 风格")
            }

            // 方式2：协程风格
            Button(
                onClick = {
                    lifecycleScope.launch {
                        fetchReposWithCoroutine("square") { state ->
                            uiState = state
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text("方式2: 协程风格（推荐）")
            }

            // 方式3：同步调用说明
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "⚠️ 同步调用会阻塞主线程！\n\n实际项目中不要这样做。",
                        Toast.LENGTH_LONG
                    ).show()
                    uiState = UiState.Error(getSyncCallWarning())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("方式3: 同步调用（演示）")
            }

            // 结果显示区域
            ResultDisplay(uiState = uiState)
        }
    }

    /**
     * 结果显示组件
     */
    @Composable
    fun ResultDisplay(uiState: UiState) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                when (uiState) {
                    is UiState.Idle -> {
                        Text(
                            text = "点击按钮开始请求",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UiState.Loading -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("加载中...")
                        }
                    }
                    is UiState.Success -> {
                        SuccessContent(repos = uiState.repos, method = uiState.method)
                    }
                    is UiState.Error -> {
                        ErrorContent(message = uiState.message)
                    }
                }
            }
        }
    }

    /**
     * 成功状态的内容显示
     */
    @Composable
    fun SuccessContent(repos: List<Repo>, method: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "✅ $method 成功！",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "找到 ${repos.size} 个仓库\n",
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Divider()

            repos.take(5).forEach { repo ->
                RepoItem(repo = repo)
                Divider()
            }

            if (repos.size > 5) {
                Text(
                    text = "\n... 还有 ${repos.size - 5} 个仓库",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    /**
     * 单个仓库项
     */
    @Composable
    fun RepoItem(repo: Repo) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "📦 ${repo.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = "   作者: ${repo.owner.login}")
            Text(text = "   ⭐ ${repo.stargazersCount} stars")
            repo.language?.let {
                Text(text = "   💻 $it")
            }
            repo.description?.let {
                Text(
                    text = "   📝 $it",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = "   🔗 ${repo.htmlUrl}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    /**
     * 错误状态的内容显示
     */
    @Composable
    fun ErrorContent(message: String) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = message,
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.error
            )
        }
    }

    /**
     * 方式1：传统的 Callback 方式
     *
     * 学习要点：
     * 1. Call.enqueue() 异步执行请求
     * 2. onResponse() 在主线程回调
     * 3. 需要手动处理成功和失败
     *
     * 🔥 调试入口：
     * 👉 在 listRepos() 这行打断点
     * 👉 Step Into 进入，观察 Retrofit 如何创建 Call 对象
     */
    private fun fetchReposWithCallback(username: String, onStateChange: (UiState) -> Unit) {
        onStateChange(UiState.Loading)

        val call = RetrofitClient.gitHubService.listRepos(username)

        // 🔥 在这里打断点，Step Into 进入 enqueue()
        call.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                if (response.isSuccessful) {
                    val repos = response.body()
                    if (repos != null) {
                        onStateChange(UiState.Success(repos, "Callback 方式"))
                        Log.d(TAG, "Success: ${repos.size} repos")
                    } else {
                        onStateChange(UiState.Error("响应体为空"))
                    }
                } else {
                    Log.e(TAG, "Error: ${response.code()}")
                    onStateChange(UiState.Error("错误: ${response.code()}\n${response.message()}"))
                }
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e(TAG, "Failure", t)
                onStateChange(UiState.Error("失败: ${t.message}\n\n${t.stackTraceToString()}"))
            }
        })
    }

    /**
     * 方式2：使用协程（推荐）
     *
     * 学习要点：
     * 1. suspend 函数自动处理线程切换
     * 2. 代码更简洁，像同步代码一样
     * 3. 异常处理更优雅
     *
     * 🔥 调试入口：
     * 👉 在 listReposAsync() 这行打断点
     * 👉 对比与 Callback 方式的区别
     */
    private suspend fun fetchReposWithCoroutine(username: String, onStateChange: (UiState) -> Unit) {
        onStateChange(UiState.Loading)

        try {
            // 🔥 在这里打断点
            val repos = RetrofitClient.gitHubService.listReposAsync(username)
            onStateChange(UiState.Success(repos, "协程方式"))
            Log.d(TAG, "Success: ${repos.size} repos")
        } catch (e: Exception) {
            Log.e(TAG, "Error", e)
            onStateChange(UiState.Error("错误: ${e.message}\n\n${e.stackTraceToString()}"))
        }
    }

    /**
     * 同步调用警告说明
     */
    private fun getSyncCallWarning(): String {
        return """
            ⚠️ 同步调用演示
            
            同步调用 call.execute() 会阻塞当前线程，
            在 Android 主线程中会导致 ANR（应用无响应）。
            
            正确做法：
            1. 使用 call.enqueue() 异步调用（方式1）
            2. 使用协程 + suspend 函数（方式2，推荐）
            3. 如果必须用 execute()，在子线程调用
            
            源码学习建议：
            - 对比 execute() 和 enqueue() 的实现
            - 理解 Retrofit 的线程切换机制
            - 查看 CallAdapter 如何处理不同的调用方式
        """.trimIndent()
    }
}
