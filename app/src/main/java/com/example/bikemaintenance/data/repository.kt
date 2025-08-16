package com.example.bikemaintenance.data

import androidx.compose.runtime.mutableStateListOf

object Repository {
    val fuelEntries = mutableStateListOf<FuelEntry>()
    val serviceEntries = mutableStateListOf<ServiceEntry>()
    val costEntries = mutableStateListOf<CostEntry>()

    fun addFuelEntry(entry: FuelEntry) {
        fuelEntries.add(entry)
    }

    fun addServiceEntry(entry: ServiceEntry) {
        serviceEntries.add(entry)
    }

    fun addCostEntry(entry: CostEntry) {
        costEntries.add(entry)
    }
}
