package com.example.warehouse.service

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


object Constants {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://feoioqyiuxazlfudaeac.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZlb2lvcXlpdXhhemxmdWRhZWFjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjkwNjkxOTEsImV4cCI6MjA0NDY0NTE5MX0.kWJ11ejcW2P-Rj7d6g0d5ZQqnkBXFfH8dHc23uSElvs"
    ) {
        install(Auth)
        install(Postgrest)
    }
}