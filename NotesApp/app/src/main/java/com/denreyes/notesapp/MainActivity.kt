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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.sp
import com.denreyes.notesapp.data.Note
import com.denreyes.notesapp.ui.theme.NotesAppTheme
import com.denreyes.notesapp.viewmodel.NotesViewModel
import com.google.android.material.chip.Chip
import org.w3c.dom.Text
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class MainActivity : ComponentActivity() {

    val viewModel = NotesViewModel()

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
                        modifier = Modifier.padding(innerPadding).padding(horizontal = 12.dp)
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
                    var toastMsg = ""
                    if (string.isNotBlank()) {
                        viewModel.notes.add(Note(text = string))
                        toastMsg = "New note added :)"
                        string = ""
                    } else {
                        toastMsg = "You cannot add an empty note."
                    }
                    Toast.makeText(context, toastMsg, Toast.LENGTH_LONG).show()
                }, modifier = Modifier.wrapContentWidth()) {
                    Text("Add Note")
                }
            }
            if (viewModel.notes.isEmpty()) {
                Column(modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text("Notes empty")
                }
            } else {
                LazyColumn {
                    items(viewModel.notes) {
                        NoteItem(it.text, it.timestamp, it.id)
                    }
                }
            }
        }
    }

    @Composable
    fun NoteItem(text: String, timestamp: Long, id: String) {
        Column(modifier = Modifier
            .padding(bottom = 12.dp)) {
            Card {
                Column(modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 12.dp)
                    .fillMaxWidth()) {
                    Text(text = text)
                    Row {
                        Text(text = id, fontSize = 8.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFB3E5FC),
                                contentColor = Color(0xFF1976D2)
                            )
                        ) {
                            Text(
                                text = Instant.ofEpochMilli(timestamp)
                                    .atZone(ZoneId.systemDefault()).format(
                                        DateTimeFormatter.ofPattern("MMM dd, YYYY hh:mm a")
                                    ),
                                fontSize = 9.sp,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            )
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