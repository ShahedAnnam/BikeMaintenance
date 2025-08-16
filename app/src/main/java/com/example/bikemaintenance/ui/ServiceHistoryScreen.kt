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
import com.example.bikemaintenance.data.ServiceEntry
import com.example.bikemaintenance.data.Repository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ServiceHistoryScreen() {
    var odometerText by remember { mutableStateOf("") }
    var serviceDescriptionText by remember { mutableStateOf("") }
    var costText by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF00C9FF), Color(0xFF92FE9D))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp)
    ) {
        Text("Service History", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = odometerText,
            onValueChange = { odometerText = it },
            label = { Text("Odometer") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )
        OutlinedTextField(
            value = serviceDescriptionText,
            onValueChange = { serviceDescriptionText = it },
            label = { Text("Service Description") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )
        OutlinedTextField(
            value = costText,
            onValueChange = { costText = it },
            label = { Text("Cost (Tk)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )
        OutlinedTextField(
            value = notesText,
            onValueChange = { notesText = it },
            label = { Text("Notes (Optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (odometerText.isNotEmpty() && serviceDescriptionText.isNotEmpty() && costText.isNotEmpty()) {
                    Repository.addServiceEntry(
                        ServiceEntry(
                            date = Date(),
                            odometer = odometerText.toInt(),
                            serviceDescription = serviceDescriptionText,
                            cost = costText.toDouble(),
                            notes = if (notesText.isEmpty()) null else notesText
                        )
                    )
                    odometerText = ""
                    serviceDescriptionText = ""
                    costText = ""
                    notesText = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe91e63))
        ) {
            Text("Add Service Entry", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Service Records", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(Repository.serviceEntries.reversed()) { service ->
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(service.serviceDescription, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Odometer: ${service.odometer} km", color = Color.Gray)
                        Text("Cost: ${service.cost} Tk", fontWeight = FontWeight.Medium, color = Color(0xFF3f51b5))
                        Text("Notes: ${service.notes ?: "N/A"}", fontSize = 12.sp, color = Color.DarkGray)
                        Text(dateFormat.format(service.date), fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
