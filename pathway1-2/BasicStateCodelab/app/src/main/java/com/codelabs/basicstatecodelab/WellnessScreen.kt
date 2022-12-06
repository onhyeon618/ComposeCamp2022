import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codelabs.basicstatecodelab.WellnessTasksList

// 팁: 에디터에 WC를 입력하면 Column 구조를 쉽게 입력할 수 있는 suggestion이 뜬다.
@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {
    Column {
        StatefulCounter()
        WellnessTasksList()
    }
}