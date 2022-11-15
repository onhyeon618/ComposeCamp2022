package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent: Composable 함수가 들어갈 수 있는 영역 생성
        setContent {
            BasicsCodelabTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    // = 대신 by를 사용하면 shouldShowOnboarding.value 대신 shouldShowOnboarding를 바로 사용할 수 있다.
    // remember: 구성이 변경되어(화면 회전 등) composable이 destroy 되면 값이 사라진다.
    // 구성 변경 시에도 값을 유지하려면 rememberSaveable 사용
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            // state를 직접 넘겨주는 대신 콜백 함수를 넘김
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// Preview는 여러 개 사용 가능
@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    // 목록을 "효율적으로" 사용하려면 LazyColumn / LazyRow를 활용한다.
    // Android에서 RecyclerView, Flutter에서 ListView를 사용하는 것과 같은 이치.
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun GreetingsPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Composable
private fun Greeting(name: String) {
    // state 추가하기:

    // var expanded = false -> 이런 방식으로는 동작하지 않는다.
    // 1. it's not being tracked by Compose
    // 2. Greeting()이 호출될 때마다 expanded는 false로 다시 초기화될 것.

    // remember: recomposition 될 때마다 값을 다시 부여받지 않도록, 자신의 값을 보존하게 함
    // mutableStateOf(): composable이 사용하는 state로 지정
    val expanded = remember { mutableStateOf(false) }

    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // modifier: UI 요소의 외형 및 동작 방식 규정 ... "To add multiple modifiers to an element, you simply chain them."
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                // child element: Flutter와 달리 단순히 나열하여 사용 가능
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(
                // 함수를 전달!!
                onClick = { expanded.value = !expanded.value },
            ) {
                Text(if (expanded.value) "Show less" else "Show more")
            }
        }
    }
}

// 프리뷰 화면의 크기 설정 가능 -> widthDp, heightDp
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
    }
}