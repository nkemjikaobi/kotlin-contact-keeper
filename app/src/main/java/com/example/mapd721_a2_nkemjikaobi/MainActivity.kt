package com.example.mapd721_a2_nkemjikaobi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapd721_a2_nkemjikaobi.ui.theme.MAPD721A2NkemjikaObiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAPD721A2NkemjikaObiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactManager()
                }
            }
        }
    }
}

@Composable
fun ContactManager() {
    var contactName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("Contact Name") },
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            label = { Text("Contact Number") },
            modifier = Modifier.padding(16.dp)
        )

       Row{
           Button(
               onClick = {  },
               modifier = Modifier.padding(16.dp)
           ) {
               Text("Add Contact")
           }

           Button(
               onClick = {  },
               modifier = Modifier.padding(16.dp)
           ) {
               Text("Fetch Contacts")
           }
       }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A2NkemjikaObiTheme {
        ContactManager()
    }
}