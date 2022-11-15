package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column(modifier = modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    // backgroundColor가 primary가 될 때, 텍스트 컬러는 자동으로 onPrimary 사용
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // modifier: UI 요소의 외형 및 동작 방식 규정 ... "To add multiple modifiers to an element, you simply chain them."
        Row(modifier = Modifier.padding(24.dp)) {
            // fillMaxWidth(): 너비를 최대로 채우도록 지정할 때 사용 / weight를 사용하면 불필요
            // Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                // child element: Flutter와 달리 단순히 나열하여 사용 가능
                // default 동작: each row takes the minimum space it can
                Text(text = "Hello,")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { /* TODO */ }
            ) {
                Text("Show more")
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