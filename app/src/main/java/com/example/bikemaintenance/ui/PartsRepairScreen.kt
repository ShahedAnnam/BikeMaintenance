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
import com.example.bikemaintenance.data.Category
import com.example.bikemaintenance.data.CostEntry
import com.example.bikemaintenance.data.Repository
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PartsRepairScreen() {
    var itemNameText by remember { mutableStateOf("") }
    var costText by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category.NEW_PART) }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(16.dp)
    ) {
        Text(
            text = "Parts & Repair Costs",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Input fields
        OutlinedTextField(
            value = itemNameText,
            onValueChange = { itemNameText = it },
            label = { Text("Item / Service Name") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = costText,
            onValueChange = { costText = it },
            label = { Text("Cost") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category selection buttons
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = { selectedCategory = Category.NEW_PART },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == Category.NEW_PART) Color(0xFFff9800) else Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "New Part",
                    color = if (selectedCategory == Category.NEW_PART) Color.White else Color.Black
                )
            }
            Button(
                onClick = { selectedCategory = Category.REPAIR },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == Category.REPAIR) Color(0xFF4caf50) else Color.White
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Repair",
                    color = if (selectedCategory == Category.REPAIR) Color.White else Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add entry button
        Button(
            onClick = {
                if (itemNameText.isNotEmpty() && costText.isNotEmpty()) {
                    Repository.addCostEntry(
                        CostEntry(
                            date = Date(),
                            category = selectedCategory,
                            itemName = itemNameText,
                            cost = costText.toDouble()
                        )
                    )
                    itemNameText = ""
                    costText = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe91e63))
        ) {
            Text(text = "Add Cost Entry", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Costs History",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(Repository.costEntries) { entry ->
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(entry.date)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("${entry.itemName}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("[${entry.category}]  $formattedDate", fontSize = 12.sp, color = Color.Gray)
                        }
                        Text("${entry.cost} \u09F3", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3f51b5))
                    }
                }
            }
        }
    }
}
