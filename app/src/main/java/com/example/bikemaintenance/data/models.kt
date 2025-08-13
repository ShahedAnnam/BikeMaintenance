package com.example.bikemaintenance.data

import java.util.Date

// Model for Fuel Tracking
data class FuelEntry(
    val date: Date,
    val odometer: Int,
    val fuelAmount: Double,
    val cost: Double,
    val fuelStation: String? = null
)

// Model for Service History
data class ServiceEntry(
    val date: Date,
    val odometer: Int,
    val serviceDescription: String,
    val cost: Double,
    val notes: String? = null
)

// Model for Parts & Repair Costs
data class CostEntry(
    val date: Date,
    val category: Category,
    val itemName: String,
    val cost: Double
)

// Enum for cost category
enum class Category {
    NEW_PART,
    REPAIR
}
