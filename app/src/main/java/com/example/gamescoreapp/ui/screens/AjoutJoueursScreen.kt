package com.example.gamescoreapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gamescoreapp.data.Joueur
import com.example.gamescoreapp.data.couleursDisponibles

@Composable
fun AjoutJoueursScreen(navController: NavHostController, joueurs: SnapshotStateList<Joueur>) {
    var nouveauNom by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            "Ajoutez les joueurs",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(joueurs.size) { index ->
                JoueurItem(
                    joueur = joueurs[index],
                    onDelete = { joueurs.removeAt(index) },
                    onColorChange = { joueurs[index] = joueurs[index].copy(couleur = it) }
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = nouveauNom,
                        onValueChange = { nouveauNom = it },
                        label = { Text("Prénom") },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        if (nouveauNom.isNotBlank()) {
                            val couleursAttribuées = joueurs.map { it.couleur }.toSet()
                            val couleurDispo = couleursDisponibles.firstOrNull { it !in couleursAttribuées }
                                ?: couleursDisponibles.random()
                            joueurs.add(Joueur(nom = nouveauNom, couleur = couleurDispo))
                            nouveauNom = ""
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Ajouter joueur")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (joueurs.isNotEmpty()) navController.navigate("tirage")
        }) {
            Text("Créer la partie")
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun JoueurItem(joueur: Joueur, onDelete: () -> Unit, onColorChange: (Color) -> Unit) {
    var showColorPicker by remember { mutableStateOf(false) }
    val isColorAssigned = joueur.couleur in couleursDisponibles

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = joueur.nom,
                onValueChange = {},
                enabled = false,
                textStyle = LocalTextStyle.current.copy(
                    color = joueur.couleur,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = joueur.couleur,
                    disabledBorderColor = Color.LightGray,
                    disabledContainerColor = Color(0xFFE0E0E0)
                ),
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
                    .background(
                        brush = if (!isColorAssigned) Brush.linearGradient(couleursDisponibles) else Brush.verticalGradient(listOf(joueur.couleur, joueur.couleur)),
                        shape = CircleShape
                    )
                    .border(1.dp, Color.Black, CircleShape)
                    .clickable { showColorPicker = true }
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer joueur")
            }
        }

        if (showColorPicker) {
            AlertDialog(
                onDismissRequest = { showColorPicker = false },
                confirmButton = {},
                title = { Text("Choisir une couleur") },
                text = {
                    Column {
                        couleursDisponibles.chunked(5).forEach { row ->
                            Row {
                                row.forEach { color ->
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .padding(4.dp)
                                            .background(color, shape = CircleShape)
                                            .border(1.dp, Color.Black, CircleShape)
                                            .clickable {
                                                onColorChange(color)
                                                showColorPicker = false
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}