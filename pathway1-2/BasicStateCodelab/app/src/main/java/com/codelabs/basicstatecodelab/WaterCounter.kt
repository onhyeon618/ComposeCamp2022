import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
            // remember: forgets the object if the source location where remember is called is not invoked again
            // 영구적으로 남아있는 값은 아니고, recomposition 시에 호출되지 않으면 사라져버린다는 이야기.
            //
            // 만약 count > 0이라 if 내부 구문이 실행되어 showTask가 생성되었더라도,
            // recomposition 시 해당 조건을 만족하지 못하게 되어 아래 코드가 실행되지 않으면 기억하고 있던 showTask는 없어진다.
            // 따라서 나중에 count > 0을 다시 만족하게 될 때, showTask 값은 "새롭게" 다시 생성된다. (!= 기존 기억값)
            //
            // 만약 var showTask~ 가 if문 밖에 선언되었다면 count 값이 어떻게 바뀌든 계속 유지되었을 것.
            var showTask by remember { mutableStateOf(true) }
            if (showTask) {
                // if (showTask) 조건을 만족하지 못할 때,
                // WellnessTaskItem은 "있지만 보이지 않는 것"이 아니라 "아예 없는 것".
                // Flutter의 위젯 트리 구조를 떠올려보자.
                WellnessTaskItem(
                    onClose = { showTask = false },
                    taskName = "Have you taken your 15 minute walk today?"
                )
            }
            Text("You've had $count glasses.")
        }
        Row(Modifier.padding(top = 8.dp)) {
            // state는 여러 개의 서로 다른 위치에서도 사용할 수 있다.
            Button(onClick = { count++ }, enabled = count < 10) {
                Text("Add one")
            }
            Button(onClick = { count = 0 }, Modifier.padding(start = 8.dp)) {
                Text("Clear water count")
            }
        }
    }
}