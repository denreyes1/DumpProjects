package com.denreyes.notesapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.denreyes.notesapp.data.Note

class NotesViewModel : ViewModel() {

    var notes = mutableStateListOf<Note>()
        private set

    fun deleteNoteById(id : String): Boolean {
        val note = notes.find { it.id == id }
        if (note != null) {
            notes.remove(note)
            return true
        }
        return false
    }
}