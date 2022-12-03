import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    // 여기부터는 슬슬 Flutter와 비슷해지네...
    // 전반적으로 내용이 눈에 익은 방식이다.
    Column(modifier = modifier.padding(16.dp)) {
        // 아래 방식은 예상대로 동작하지 않는다.
        var count = 0
        Text("You've had $count glasses.")
        Button(onClick = { count++ }, Modifier.padding(top = 8.dp)) {
            Text("Add one")
        }
    }
}