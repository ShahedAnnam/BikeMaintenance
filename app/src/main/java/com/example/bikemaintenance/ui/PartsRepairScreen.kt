package com.example.bikemaintenance.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bikemaintenance.data.Category
import com.example.bikemaintenance.data.Repository
import java.util.Date

@Composable
fun PartsRepairScreen() {
    var itemName by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.NEW_PART) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Parts & Repair Costs", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item / Service Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            label = { Text("Cost") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Category selection
        Row {
            Text("Category: ", modifier = Modifier.alignByBaseline())
            Spacer(modifier = Modifier.width(8.dp))
            DropdownMenuExample(selectedCategory = category, onCategorySelected = { category = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (itemName.isNotEmpty() && cost.isNotEmpty()) {
                Repository.addCostEntry(
                    date = Date(),
                    category = category,
                    itemName = itemName,
                    cost = cost.toDouble()
                )
                itemName = ""
                cost = ""
            }
        }) {
            Text("Add Entry")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Cost History:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(Repository.costEntries) { entry ->
                Text("${entry.date}: ${entry.itemName}, ${entry.cost} currency, Category: ${entry.category}")
            }
        }
    }
}

@Composable
fun DropdownMenuExample(selectedCategory: Category, onCategorySelected: (Category) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedCategory.name)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Category.values().forEach { cat ->
                DropdownMenuItem(
                    text = { Text(cat.name) },
                    onClick = {
                        onCategorySelected(cat)
                        expanded = false
                    }
                )
            }
        }
    }
}
