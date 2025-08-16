package com.example.bikemaintenance.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikemaintenance.data.FuelEntry
import com.example.bikemaintenance.data.ServiceEntry
import com.example.bikemaintenance.data.Repository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen() {
    val fuelEntries = Repository.fuelEntries
    val serviceEntries = Repository.serviceEntries

    // Gradient background for whole screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
                )
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Bike Maintenance Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Quick stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF42A5F5))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Fuel Entries", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("${fuelEntries.size}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Total Services", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("${serviceEntries.size}", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Recent Fuel Entries", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(fuelEntries.takeLast(5).reversed()) { entry ->
                    FuelCard(entry)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Recent Services", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(serviceEntries.takeLast(5).reversed()) { entry ->
                    ServiceCard(entry)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun FuelCard(fuel: FuelEntry) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF64B5F6)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Date: ${dateFormat.format(fuel.date)}", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Odometer: ${fuel.odometer} km", color = Color.White)
            Text("Fuel: ${fuel.fuelAmount} L", color = Color.White)
            Text("Cost: ${fuel.cost}", color = Color.White)
            Text("Station: ${fuel.fuelStation ?: "N/A"}", color = Color.White)
        }
    }
}

@Composable
fun ServiceCard(service: ServiceEntry) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF42A5F5)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Date: ${dateFormat.format(service.date)}", color = Color.White, fontWeight = FontWeight.Bold)
            Text("Odometer: ${service.odometer} km", color = Color.White)
            Text("Service: ${service.serviceDescription}", color = Color.White)
            Text("Cost: ${service.cost}", color = Color.White)
            Text("Notes: ${service.notes ?: "N/A"}", color = Color.White)
        }
    }
}
