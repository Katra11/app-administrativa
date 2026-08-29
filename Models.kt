package com.kvncell.serviciotecnico.data

data class Customer(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val nit: String? = null
)

data class Repair(
    val id: String = "",
    val customerId: String = "",
    val brand: String = "",
    val model: String = "",
    val imei: String? = null,
    val reportedIssue: String = "",
    val diagnosis: String = "",
    val status: String = "RECIBIDO",
    val total: Double = 0.0,
    val deposit: Double = 0.0,
    val assignedTechnicianId: String? = null
)

data class RepairPhoto(
    val id: String = "",
    val repairId: String = "",
    val storagePath: String = "",
    val caption: String? = null
)

data class InventoryItem(
    val id: String = "",
    val name: String = "",
    val sku: String? = null,
    val stock: Int = 0,
    val minStock: Int = 0,
    val cost: Double = 0.0
)

data class AppUser(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: String = "RECEPTION"
)
