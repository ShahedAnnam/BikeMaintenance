package com.example.bikemaintenance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bikemaintenance.ui.components.TopBar
import com.example.bikemaintenance.ui.*

enum class Screen { Dashboard, Fuel, Service, Costs }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(), // or darkColorScheme()
            )  {
                var currentScreen by remember { mutableStateOf(Screen.Dashboard) }

                Scaffold(
                    topBar = { TopBar(title = "Bike Maintenance") },
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentScreen == Screen.Dashboard,
                                onClick = { currentScreen = Screen.Dashboard },
                                icon = { Icon(Icons.Default.Home, "Dashboard") },
                                label = { Text("Dashboard") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == Screen.Fuel,
                                onClick = { currentScreen = Screen.Fuel },
                                icon = { Icon(Icons.Default.LocalGasStation, "Fuel") },
                                label = { Text("Fuel") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == Screen.Service,
                                onClick = { currentScreen = Screen.Service },
                                icon = { Icon(Icons.Default.Build, "Service") },
                                label = { Text("Service") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == Screen.Costs,
                                onClick = { currentScreen = Screen.Costs },
                                icon = { Icon(Icons.Default.AttachMoney, "Costs") },
                                label = { Text("Costs") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when (currentScreen) {
                            Screen.Dashboard -> DashboardScreen()
                            Screen.Fuel -> FuelTrackingScreen()
                            Screen.Service -> ServiceHistoryScreen()
                            Screen.Costs -> PartsRepairScreen()
                        }
                    }
                }
            }
        }
    }
}
