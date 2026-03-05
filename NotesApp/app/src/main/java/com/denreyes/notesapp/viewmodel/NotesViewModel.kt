package com.denreyes.notesapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.denreyes.notesapp.data.Note

class NotesViewModel : ViewModel() {

    var notes = mutableStateListOf<Note>()
        private set
}