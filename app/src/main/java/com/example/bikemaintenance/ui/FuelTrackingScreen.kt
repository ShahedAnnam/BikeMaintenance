package com.example.bikemaintenance.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikemaintenance.data.Repository
import java.util.Date

@Composable
fun FuelTrackingScreen() {
    var odometer by remember { mutableStateOf("") }
    var fuelAmount by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var station by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Fuel Tracking", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = odometer,
            onValueChange = { odometer = it },
            label = { Text("Odometer") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = fuelAmount,
            onValueChange = { fuelAmount = it },
            label = { Text("Fuel Amount (L)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = station,
            onValueChange = { station = it },
            label = { Text("Fuel Station (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (odometer.isNotEmpty() && fuelAmount.isNotEmpty() && cost.isNotEmpty()) {
                Repository.addFuelEntry(
                    date = Date(),
                    odometer = odometer.toInt(),
                    fuelAmount = fuelAmount.toDouble(),
                    cost = cost.toDouble(),
                    fuelStation = if (station.isEmpty()) null else station
                )
                odometer = ""
                fuelAmount = ""
                cost = ""
                station = ""
            }
        }) {
            Text("Add Fuel Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Fuel History:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(Repository.fuelEntries) { fuel ->
                Text("${fuel.date}: ${fuel.fuelAmount} L at ${fuel.odometer} km, ${fuel.cost} currency, Station: ${fuel.fuelStation ?: "N/A"}")
            }
        }
    }
}
