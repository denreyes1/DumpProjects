package com.denreyes.notesapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denreyes.notesapp.data.Note
import com.denreyes.notesapp.ui.theme.NotesAppTheme
import com.denreyes.notesapp.viewmodel.NotesViewModel

class MainActivity : ComponentActivity() {

    private val viewModel = NotesViewModel()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.Blue.toArgb()
            )
        )
        setContent {
            NotesAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(R.string.app_name))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Blue,
                                titleContentColor = Color.White
                            )
                        )
                    }
                ) { innerPadding ->
                    ContentBody(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun ContentBody(modifier: Modifier = Modifier) {
        var string by remember { mutableStateOf("") }
        val context = LocalContext.current
        Column(modifier) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = string,
                    onValueChange = { string = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Type note...") })
                Spacer(modifier.width(8.dp))
                Button(onClick = {
                    if (string.isNotBlank()) {
                        val noteEntry = Note(text = string)
                        viewModel.notes.add(noteEntry)
                        Toast.makeText(context, "Note added :)", Toast.LENGTH_LONG).show()
                        string = ""
                    }
                }, modifier = Modifier.wrapContentWidth()) {
                    Text("Add Note")
                }
            }
            val notesList = viewModel.notes
            if(notesList.isEmpty()) {
                notesList.add(Note(text = "Test1 "))
                notesList.add(Note(text = "Test 2"))
                notesList.add(Note( text = "Test 3"))
            }
            
            LazyColumn() {
                items(notesList){
                    Column(modifier = Modifier.padding(bottom = 12.dp)) {
                        Card {
                            Text(text = it.text, modifier = Modifier.padding(12.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentBody() {
    NotesAppTheme {
        ContentBody()
    }
}