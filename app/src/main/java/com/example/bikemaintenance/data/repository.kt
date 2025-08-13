package com.example.bikemaintenance.data

import androidx.compose.runtime.mutableStateListOf
import java.util.Date

object Repository {

    // Lists to store entries
    val fuelEntries = mutableStateListOf<FuelEntry>()
    val serviceEntries = mutableStateListOf<ServiceEntry>()
    val costEntries = mutableStateListOf<CostEntry>()

    // Fuel
    fun addFuelEntry(
        date: Date,
        odometer: Int,
        fuelAmount: Double,
        cost: Double,
        fuelStation: String? = null
    ) {
        fuelEntries.add(FuelEntry(date, odometer, fuelAmount, cost, fuelStation))
    }

    // Service
    fun addServiceEntry(
        date: Date,
        odometer: Int,
        serviceDescription: String,
        cost: Double,
        notes: String? = null
    ) {
        serviceEntries.add(ServiceEntry(date, odometer, serviceDescription, cost, notes))
    }

    // Parts & Repair Costs
    fun addCostEntry(
        date: Date,
        category: Category,
        itemName: String,
        cost: Double
    ) {
        costEntries.add(CostEntry(date, category, itemName, cost))
    }
}
