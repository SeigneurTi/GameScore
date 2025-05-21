package com.example.gamescoreapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gamescoreapp.data.Joueur
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun JoueurSection(joueur: Joueur, isActif: Boolean, onScoreAjoute: () -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }
    var nouveauScore by remember { mutableStateOf("") }

    val total = joueur.scores.sumOf { it.toIntOrNull() ?: 0 }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = joueur.couleur.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        joueur.nom,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (joueur.scores.isNotEmpty()) {
                        Text(
                            "Total : $total",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle expand"
                    )
                }
            }

            if (isActif) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    OutlinedTextField(
                        value = nouveauScore,
                        onValueChange = { nouveauScore = it },
                        label = { Text("Nouveau score") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        if (nouveauScore.isNotBlank()) {
                            if (joueur.scores.isEmpty()) {
                                joueur.scores.add(nouveauScore) // Ceci devient Score 1
                            } else {
                                joueur.scores.add(nouveauScore)
                            }
                            nouveauScore = ""
                            onScoreAjoute()
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Ajouter un score")
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded && joueur.scores.isNotEmpty()) {
                Column {
                    joueur.scores.forEachIndexed { i, score ->
                        OutlinedTextField(
                            value = score,
                            onValueChange = {
                                if (isActif) joueur.scores[i] = it
                            },
                            label = { Text("Score ${i + 1}") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            enabled = isActif,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}