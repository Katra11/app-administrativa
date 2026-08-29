package com.kvncell.serviciotecnico

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*

data class RepairOrder(
    val id: String,
    val customer: String,
    val phone: String,
    val device: String,
    val status: String,
    val total: Double,
    val balance: Double
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KvncellApp(::openWhatsApp) }
    }

    private fun openWhatsApp(phone: String, message: String) {
        val uri = Uri.parse("https://wa.me/${phone.replace("+","")}?text=${Uri.encode(message)}")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

@Composable
fun KvncellApp(whatsapp: (String,String)->Unit) {
    val nav = rememberNavController()
    MaterialTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("KVNCELL Servicio Técnico") }) },
            bottomBar = {
                NavigationBar {
                    listOf(
                        "Inicio" to "home",
                        "Reparaciones" to "repairs",
                        "Clientes" to "customers",
                        "Inventario" to "inventory"
                    ).forEach { (label, route) ->
                        NavigationBarItem(
                            selected = false,
                            onClick = { nav.navigate(route) },
                            icon = { Icon(Icons.Default.Build, null) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { pad ->
            NavHost(nav, startDestination = "home", Modifier.padding(pad)) {
                composable("home") { Dashboard() }
                composable("repairs") { Repairs(whatsapp) }
                composable("customers") { SimplePage("Clientes", "Agregar cliente • NIT opcional • Historial") }
                composable("inventory") { SimplePage("Inventario", "Repuestos • Stock • Alertas") }
            }
        }
    }
}

@Composable
fun Dashboard() {
    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Resumen de hoy", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(Modifier.weight(1f)) { Text("12\nReparaciones", Modifier.padding(16.dp)) }
            Card(Modifier.weight(1f)) { Text("5\nListas", Modifier.padding(16.dp)) }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(Modifier.weight(1f)) { Text("Q2,450\nIngresos", Modifier.padding(16.dp)) }
            Card(Modifier.weight(1f)) { Text("3\nPendientes", Modifier.padding(16.dp)) }
        }
        Text("Conectores de backend listos: Firebase / Supabase", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Repairs(whatsapp: (String,String)->Unit) {
    val orders = listOf(
        RepairOrder("KV-0001","Cliente demo","50255555555","Samsung A54","Listo",350.0,250.0),
        RepairOrder("KV-0002","Cliente demo 2","50255555556","iPhone 13","En reparación",550.0,550.0)
    )
    LazyColumn(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Button(onClick = { }) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(8.dp)); Text("Nueva reparación") }
        }
        items(orders) { o ->
            Card {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(o.id, style = MaterialTheme.typography.titleMedium)
                    Text("${o.customer} • ${o.device}")
                    Text("Estado: ${o.status}")
                    Text("Total: Q${o.total} • Saldo: Q${o.balance}")
                    Button(onClick = {
                        whatsapp(o.phone, "Hola ${o.customer}, le informamos que su ${o.device} está en estado: ${o.status}. Total: Q${o.total}. Saldo: Q${o.balance}. Gracias por confiar en KVNCELL.")
                    }) { Text("Avisar por WhatsApp") }
                }
            }
        }
    }
}

@Composable
fun SimplePage(title: String, subtitle: String) {
    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(subtitle)
        Button(onClick = {}) { Text("Agregar") }
    }
}
