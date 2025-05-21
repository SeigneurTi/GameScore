package com.example.gamescoreapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun MenuScreen(navController: NavHostController) {
    var afficherBouton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        afficherBouton = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 320.dp), // 🔝 position haute fixe
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🎮 Score App 🎯",
                fontSize = 48.sp,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = afficherBouton,
                enter = fadeIn(animationSpec = tween(durationMillis = 2500))
            ) {
                Button(onClick = { navController.navigate("ajout_joueurs") }) {
                    Text("Commencer")
                }
            }
        }

        Text(
            text = "© SeigneurTi",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}
