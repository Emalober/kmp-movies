import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.emalober.kmpmovies.Navigation
import com.emalober.kmpmovies.data.database.MoviesDao
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(moviesDao: MoviesDao) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    Navigation(moviesDao)
}
