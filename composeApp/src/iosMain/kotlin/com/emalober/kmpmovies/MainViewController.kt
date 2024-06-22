import androidx.compose.ui.window.ComposeUIViewController
import com.emalober.kmpmovies.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val db = getDatabaseBuilder().build()
    App(db.moviesDao())
}