@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.bikemaintenance.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun TopBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(title) }
    )
}
