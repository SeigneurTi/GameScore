package com.example.gamescoreapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.gamescoreapp.data.Joueur
import kotlinx.coroutines.delay

@Composable
fun TirageScreen(
    navController: NavHostController,
    joueurs: List<Joueur>,
    joueurChoisi: MutableState<Joueur?>
) {
    var etape by remember { mutableStateOf(0) } // 0: intro, 1: main, 2: sac, 3: flèche, 4: tirage
    val nomsAffiches = remember { mutableStateListOf<String>() }
    val tirage = remember { mutableStateOf<Joueur?>(null) }
    val currentIndex = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        // Phase 0 → main prend les prénoms
        delay(500)
        etape = 1
        while (currentIndex.value < joueurs.size) {
            nomsAffiches.add(joueurs[currentIndex.value].nom)
            currentIndex.value++
            delay(300)
        }

        // Phase 1 → met dans le sac
        delay(800)
        etape = 2

        // Phase 2 → flèche tourne
        delay(1200)
        etape = 3

        // Phase 3 → résultat tiré
        delay(1500)
        tirage.value = joueurs.random()
        joueurChoisi.value = tirage.value
        etape = 4
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎩 Tirage au sort", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(visible = etape == 1) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("✋ La main prend les prénoms...")
                Spacer(modifier = Modifier.height(8.dp))
                nomsAffiches.forEach {
                    Text("• $it", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        AnimatedVisibility(visible = etape == 2) {
            Text("🎒 La main dépose les prénoms dans le sac...")
        }

        AnimatedVisibility(visible = etape == 3) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🔄 La flèche tourne...")
                Spacer(modifier = Modifier.height(12.dp))
                CircularProgressIndicator()
            }
        }

        AnimatedVisibility(visible = etape == 4 && tirage.value != null) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Le joueur qui commence est :", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = tirage.value!!.nom,
                    style = MaterialTheme.typography.headlineMedium,
                    color = tirage.value!!.couleur
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { navController.navigate("score") }) {
                    Text("C’est parti !")
                }
            }
        }
    }
}
