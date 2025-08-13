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
fun ServiceHistoryScreen() {
    var odometer by remember { mutableStateOf("") }
    var serviceDescription by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Service History", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = odometer,
            onValueChange = { odometer = it },
            label = { Text("Odometer") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = serviceDescription,
            onValueChange = { serviceDescription = it },
            label = { Text("Service Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (odometer.isNotEmpty() && serviceDescription.isNotEmpty() && cost.isNotEmpty()) {
                Repository.addServiceEntry(
                    date = Date(),
                    odometer = odometer.toInt(),
                    serviceDescription = serviceDescription,
                    cost = cost.toDouble(),
                    notes = notes.ifEmpty { null }  // properly handle optional notes
                )
                odometer = ""
                serviceDescription = ""
                cost = ""
                notes = ""
            }
        }) {
            Text("Add Service Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Service History List:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(Repository.serviceEntries) { service ->
                Text("${service.date}: ${service.serviceDescription} at ${service.odometer} km, ${service.cost} currency, Notes: ${service.notes ?: "N/A"}")
            }
        }
    }
}
