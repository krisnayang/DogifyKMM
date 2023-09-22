import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    application {
        Window(
            title = "Desktop App",
            onCloseRequest = ::exitApplication
        ) {
            hello()
        }
    }
}

@Composable
fun hello() {
    Text("Hello world")
}