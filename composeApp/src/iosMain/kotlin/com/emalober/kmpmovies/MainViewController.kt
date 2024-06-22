import androidx.compose.ui.window.ComposeUIViewController
import com.emalober.kmpmovies.data.database.getDatabaseBuilder
import com.emalober.kmpmovies.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}