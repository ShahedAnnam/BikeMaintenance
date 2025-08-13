package com.example.bikemaintenance.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikemaintenance.data.Repository
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DashboardScreen() {
    val fuelList = Repository.fuelEntries
    val serviceList = Repository.serviceEntries

    val lastFuel = fuelList.lastOrNull()
    val lastService = serviceList.lastOrNull()

    val mileage = if (fuelList.size >= 2) {
        val prev = fuelList[fuelList.size - 2]
        (lastFuel?.odometer?.minus(prev.odometer)?.toDouble() ?: 0.0) / (prev.fuelAmount)
    } else 0.0

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Last Fuel Fill-up: ${lastFuel?.odometer ?: "N/A"} km")
        Text(text = "Mileage since last fill-up: ${"%.2f".format(mileage)} km/L")
        Text(text = "Last Service Date: ${lastService?.date?.let { formatDate(it) } ?: "N/A"}")

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Recent Fuel Entries:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(fuelList) { fuel ->
                Text(text = "${fuel.date}: ${fuel.fuelAmount} L at ${fuel.odometer} km")
            }
        }
    }
}

fun formatDate(date: java.util.Date): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(date)
}
