package com.denreyes.notesapp.data

import java.util.UUID

data class Note (
    val id : String = UUID.randomUUID().toString(),
    val text : String,
    val timestamp: Long = System.currentTimeMillis()
)