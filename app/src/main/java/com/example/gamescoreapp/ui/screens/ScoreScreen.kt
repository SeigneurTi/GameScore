package com.example.gamescoreapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gamescoreapp.data.Joueur
import com.example.gamescoreapp.ui.components.JoueurSection

@Composable
fun ScoreScreen(joueurs: List<Joueur>, joueurQuiCommence: Joueur) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var indexEnCours by remember { mutableStateOf(joueurs.indexOf(joueurQuiCommence)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = screenWidth * 0.05f)
    ) {
        Text(
            text = "C’est au tour de ${joueurs[indexEnCours].nom}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge
        )

        LazyColumn {
            items(joueurs.size) { index ->
                JoueurSection(
                    joueur = joueurs[index],
                    isActif = index == indexEnCours,
                    onScoreAjoute = {
                        indexEnCours = (indexEnCours + 1) % joueurs.size
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
