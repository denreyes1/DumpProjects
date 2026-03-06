package com.denreyes.notesapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.denreyes.notesapp.data.Note
import com.denreyes.notesapp.ui.theme.NotesAppTheme
import com.denreyes.notesapp.viewmodel.NotesViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    val viewModel: NotesViewModel by viewModels()

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
                        modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
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
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)) {
                TextField(
                    value = string,
                    onValueChange = { string = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Type note...") })
                Spacer(modifier.width(8.dp))
                Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    onClick = {
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
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    items(viewModel.notes) {
                        NoteItem(it.text, it.timestamp, it.id)
                    }
                }
            }
        }
    }

    @Composable
    fun NoteItem(text: String, timestamp: Long, id: String) {
        val context = LocalContext.current
        Row(modifier = Modifier
            .padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Card(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier
                    .padding(horizontal = 18.dp, vertical = 12.dp)
                ){
                    Text(text = text)
                    Row {
                        Text(
                            text = id, fontSize = 8.sp, color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )

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
            IconButton(
                onClick = {
                    val msg = if (viewModel.deleteNoteById(id)) "Note has been deleted." else "Something went wrong when deleting."
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                },
                colors = IconButtonDefaults.iconButtonColors(containerColor = Color(0xFF1976D2), contentColor = Color.White),
                modifier = Modifier.padding(start = 12.dp)
            ) {
                Icon (
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ContentBodyPreview() {
        viewModel.notes.add(Note(text = "Note"))
        NotesAppTheme {
            ContentBody(modifier = Modifier)
        }
    }
}