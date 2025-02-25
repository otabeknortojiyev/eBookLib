package uz.gita.otabek.ebooklib

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.otabek.ebooklib.local.BookDataBase
import uz.gita.otabek.ebooklib.repository.AppRepository
import uz.gita.otabek.ebooklib.screens.splash.SplashScreen
import uz.gita.otabek.ebooklib.ui.navigation.NavigationHandler
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigationHandler: NavigationHandler

    @Inject
    lateinit var repository: AppRepository

    @Inject
    lateinit var db: BookDataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository.loadAllBooksFromNetwork(onSuccess = {
            db.bookDao().insertBooks(it)
        }) {}
        repository.loadAuthorsFromNetwork(onSuccess = {
            db.bookDao().insertAuthors(it)
        }) {}
        setContent {
            Navigator(screen = SplashScreen, onBackPressed = {
                true
            }) { navigator ->
                LaunchedEffect(key1 = navigator) {
                    navigationHandler.screenState.onEach {
                        it.invoke(navigator)
                    }.launchIn(lifecycleScope)
                }
                SlideTransition(navigator = navigator)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCacheAndFiles()
    }

    private fun clearCacheAndFiles() {
        try {
            val cacheDir = cacheDir
            if (cacheDir.isDirectory) {
                val cacheResult = deleteDir(cacheDir)
                Log.d("TTT", "clearCache: $cacheResult")
            }
            val filesDir = filesDir
            if (filesDir.isDirectory) {
                val filesResult = deleteDir(filesDir)
                Log.d("TTT", "clearFiles: $filesResult")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            for (child in children) {
                val success = deleteDir(File(dir, child))
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }
}
