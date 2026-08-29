package com.kvncell.serviciotecnico.data

object BackendConfig {
    // Selecciona FIREBASE o SUPABASE al conectar el backend.
    const val PROVIDER = "SUPABASE"

    // No colocar secretos reales en código público.
    const val SUPABASE_URL = "YOUR_SUPABASE_URL"
    const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_ANON_KEY"
}
