package com.daveloper.rickandmortyapp.core.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.daveloper.rickandmortyapp.R
import com.daveloper.rickandmortyapp.core.base.result.RepositoryResult
import com.daveloper.rickandmortyapp.core.ui.theme.RickMortyAppTheme
import com.daveloper.rickandmortyapp.feature_character.data.repository.external.CharacterRepository
import com.daveloper.rickandmortyapp.feature_character.domain.GetCharactersUseCase
import com.daveloper.rickandmortyapp.feature_character.presentation.characters.components.CharactersScreen
import com.daveloper.rickandmortyapp.feature_episode.data.repository.external.EpisodeRepository
import com.daveloper.rickandmortyapp.feature_episode.presentation.episodes.components.EpisodesScreen
import com.daveloper.rickandmortyapp.feature_location.data.repository.external.LocationRepository
import com.daveloper.rickandmortyapp.feature_location.presentation.locations.components.LocationsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }
    @Inject
    lateinit var characterRepository: CharacterRepository

    @Inject
    lateinit var episodeRepository: EpisodeRepository

    @Inject
    lateinit var locationRepository: LocationRepository

    @Inject
    lateinit var getCharactersUseCase: GetCharactersUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            RickMortyAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    //CharactersScreen()
                    //EpisodesScreen()
                    LocationsScreen()
                    //BaseInitApp(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    @Composable
    fun BaseInitApp(modifier: Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Button(
                onClick = {
                    lifecycleScope.launch (Dispatchers.IO){
                        characterRepository.getCharacters(true)
                    }
                    lifecycleScope.launch (Dispatchers.IO){
                        episodeRepository.getEpisodes(true)
                    }
                    lifecycleScope.launch (Dispatchers.IO){
                        locationRepository.getLocations(true)
                    }
                    lifecycleScope.launch {
                        getCharactersUseCase.invoke().collectLatest {
                            Log.i(TAG, "BaseInitApp characters found: ${it.size}")
                        }
                    }
                }
            ) {
                Text(text = "Test request")
            }
        }
    }
}