package com.example.mapd721_a2_nkemjikaobi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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

/**
 * Student Name - Nkemjika Obi
 * Student Number - 301275091
 */

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
                    ContactManager(context = this)
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

        Text(
            text = "Nkemjika Obi",
            modifier = Modifier.padding(
                start = 16.dp,
                top = 10.dp,
                end = 16.dp,
                bottom = 10.dp
            ),
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = "301275091",
            modifier = Modifier.padding(
                start = 16.dp,
                top = 0.dp,
                end = 16.dp,
                bottom = 20.dp
            ),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )

        //Text fields
        OutlinedTextField(
            value = contactName,
            onValueChange = { contactName = it },
            label = { Text("Contact Name") },
            modifier = Modifier.padding(16.dp),
//            isError = checkIfFieldIsEmpty  contactName.isBlank()
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
                onClick = {
                    if (contactName.isNotBlank() && contactNumber.isNotBlank()) {
                        Log.d("", "ran this method")
                        val success = addContact(context, contactName, contactNumber)
                        if (success) {
                            Log.d("", "it was successful")
                            // Clear input fields after successful addition
                            contactName = ""
                            contactNumber = ""
                            contacts = fetchContacts(context)
                        } else {
                            Log.d("", "it failed")
                            // Handle failure to add contact
                        }
                    } else {
                        Log.d("", "fields were empty")
                        // Handle empty input fields
                    }
                },
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


fun addContact(context: ComponentActivity, name: String, number: String): Boolean {

    val intent = Intent(Intent.ACTION_INSERT).apply {
        type = ContactsContract.Contacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.NAME, name)
        putExtra(ContactsContract.Intents.Insert.PHONE, number)
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
        return true
    } else {
        return false
    }

//    val values = ContentValues().apply{
//        put(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name)
//        put(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
//    }
//
//    val uri = context.contentResolver.insert(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, values)
//    return uri != null

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A2NkemjikaObiTheme {
        ContactManager(context = ComponentActivity())
    }
}