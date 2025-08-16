package com.example.bikemaintenance.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.example.bikemaintenance.data.FuelEntry
import com.example.bikemaintenance.data.Repository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FuelTrackingScreen() {
    var odometer by remember { mutableStateOf("") }
    var fuelAmount by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var station by remember { mutableStateOf("") }

    // --- Ensure default entry exists ---
    if (Repository.fuelEntries.isEmpty()) {
        Repository.addFuelEntry(
            FuelEntry(
                date = Date(),
                odometer = 0,
                fuelAmount = 0.0,
                cost = 0.0,
                fuelStation = "N/A"
            )
        )
    }

    val fuelEntries = Repository.fuelEntries

    // --- Calculate stats ---
    val lastOdometer = fuelEntries.maxByOrNull { it.date }?.odometer ?: 0
    val mileage: Double? = if (fuelEntries.size >= 2) {
        val sorted = fuelEntries.sortedBy { it.date }
        val mileages = sorted.zipWithNext { prev, next ->
            val distance = (next.odometer - prev.odometer).toDouble()
            if (next.fuelAmount > 0) distance / next.fuelAmount else null
        }.filterNotNull()
        if (mileages.isNotEmpty()) mileages.average() else 0.0
    } else 0.0

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp)
    ) {
        Text("Fuel Tracking", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        // --- Stats cards ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Last Odometer", fontWeight = FontWeight.SemiBold, color = Color(0xFF3f51b5))
                    Text("$lastOdometer km", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Mileage", fontWeight = FontWeight.SemiBold, color = Color(0xFF3f51b5))
                    Text(
                        String.format("%.2f km/L", mileage),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Input fields ---
        OutlinedTextField(
            value = odometer,
            onValueChange = { odometer = it },
            label = { Text("Odometer") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        OutlinedTextField(
            value = fuelAmount,
            onValueChange = { fuelAmount = it },
            label = { Text("Fuel Amount (L)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost (Tk)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        OutlinedTextField(
            value = station,
            onValueChange = { station = it },
            label = { Text("Fuel Station (Optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (odometer.isNotEmpty() && fuelAmount.isNotEmpty() && cost.isNotEmpty()) {
                    Repository.addFuelEntry(
                        FuelEntry(
                            date = Date(),
                            odometer = odometer.toInt(),
                            fuelAmount = fuelAmount.toDouble(),
                            cost = cost.toDouble(),
                            fuelStation = if (station.isEmpty()) null else station
                        )
                    )
                    odometer = ""
                    fuelAmount = ""
                    cost = ""
                    station = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe91e63))
        ) {
            Text("Add Fuel Entry", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Fuel History", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))

        // --- Show history excluding the default entry ---
        LazyColumn {
            items(fuelEntries.filter { it.odometer != 0 }.reversed()) { entry ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Odometer: ${entry.odometer} km", fontWeight = FontWeight.Bold)
                            Text("Fuel: ${entry.fuelAmount} L", color = Color.Gray)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Cost: ${entry.cost} Tk", fontWeight = FontWeight.Bold, color = Color(0xFF3f51b5))
                            Text(dateFormat.format(entry.date), fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
