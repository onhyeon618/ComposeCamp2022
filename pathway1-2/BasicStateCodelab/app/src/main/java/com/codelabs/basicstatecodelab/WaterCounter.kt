import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // val count: MutableState<Int> = remember { mutableStateOf(0) }
        // -> 사용할 때는 count.value++와 같이(처럼) 사용

        // 위 방식을 보다 간소하게 사용하려면 다음과 같이 쓸 수 있다:
        var count by remember { mutableStateOf(0) }

        if (count > 0) {
            // This text is present if the button has been clicked
            // at least once; absent otherwise
            Text("You've had $count glasses.")
        }
        // state는 여러 개의 서로 다른 위치에서도 사용할 수 있다.
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp), enabled = count < 10) {
            Text("Add one")
        }
    }
}