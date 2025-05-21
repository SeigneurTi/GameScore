package com.example.gamescoreapp.data

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color

data class Joueur(
    var nom: String,
    var couleur: Color = Color.White,
    var scores: SnapshotStateList<String> = mutableStateListOf()
)

val couleursDisponibles = listOf(
    Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Cyan,
    Color.Yellow, Color.Gray, Color.Black, Color.DarkGray, Color.LightGray,
    Color(0xFFFF5722), Color(0xFF795548), Color(0xFF607D8B),
    Color(0xFF009688), Color(0xFF3F51B5)
)
