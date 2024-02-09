package com.example.mapd721_a2_nkemjikaobi

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    ContactManager(context =  this)
                }
            }
        }
    }
}

@Composable
fun ContactManager(context: ComponentActivity) {
    var contactName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var contacts by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize()) {

        //Text fields
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

        Row {

            //Buttons
            Button(
                onClick = { },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Add Contact")
            }

            Button(
                onClick = { contacts = fetchContacts(context) },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Fetch Contacts")
            }
        }

        Text(
            text = "Contacts",
            modifier = Modifier.padding(16.dp),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )

        //List of contacts
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(contacts) { contact ->
                Text(text = contact, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

//@SuppressLint("Range")
//fun loadContacts(context: ComponentActivity): List<Contact> {
//    val contacts = mutableListOf<Contact>()
//
//    // Use the content resolver to query contacts
//    context.contentResolver.query(
//        ContactsContract.Contacts.CONTENT_URI,
//        arrayOf(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),
//        null,
//        null,
//        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
//    )?.use { cursor ->
//        // Check if the cursor has data
//        if (cursor.moveToFirst()) {
//            do {
//                // Retrieve display name from the cursor and add to the list
//                val displayName =
//                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
//                contacts.add(Contact(displayName))
//            } while (cursor.moveToNext())
//        }
//    }
//
//    return contacts
//}

@SuppressLint("Range")
//Method to fetch contacts
fun fetchContacts(context: ComponentActivity): List<String> {
    val contactList = mutableListOf<String>()
    val cursor = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        while (it.moveToNext()) {
            val name =
                it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contactList.add("$name - $number")
        }
    }

    return contactList
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
        ContactManager(context =  ComponentActivity())
    }
}