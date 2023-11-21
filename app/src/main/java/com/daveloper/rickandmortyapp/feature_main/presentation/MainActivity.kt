package com.daveloper.rickandmortyapp.feature_main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.daveloper.rickandmortyapp.core.ui.theme.RickMortyAppTheme
import com.daveloper.rickandmortyapp.feature_main.presentation.components.MainNavigationCmp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RickMortyAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigationCmp()
                }
            }
        }
        setUpKeyboardViewResize()
    }

    private fun setUpKeyboardViewResize() {
        ViewCompat
            .setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content)
            ) { view, insets ->
                val bottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom
                view.updatePadding(bottom = bottom)
                insets
            }
    }
}