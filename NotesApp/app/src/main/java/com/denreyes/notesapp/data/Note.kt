package com.denreyes.notesapp.data

import java.util.UUID

//id, text, timestamp
data class Note (
    val id : String = UUID.randomUUID().toString(),
    val text : String,
    val timestamp : Long = System.currentTimeMillis()
)