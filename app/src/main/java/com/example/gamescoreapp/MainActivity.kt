package com.example.gamescoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.gamescoreapp.data.Joueur
import com.example.gamescoreapp.ui.screens.*
import com.example.gamescoreapp.ui.theme.GameScoreAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        setContent {
            GameScoreAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val joueurs = remember { mutableStateListOf<Joueur>() }
    val joueurCommence = remember { mutableStateOf<Joueur?>(null) }

    NavHost(navController = navController, startDestination = "menu") {
        composable("menu") { MenuScreen(navController) }
        composable("ajout_joueurs") { AjoutJoueursScreen(navController, joueurs) }
        composable("tirage") {
            TirageScreen(
                navController = navController,
                joueurs = joueurs,
                joueurChoisi = joueurCommence
            )
        }
        composable("score") {
            joueurCommence.value?.let {
                ScoreScreen(joueurs = joueurs, joueurQuiCommence = it)
            }
        }
    }
}
