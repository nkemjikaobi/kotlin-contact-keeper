package com.example.mapd721_a2_nkemjikaobi

import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    var searchQuery by remember { mutableStateOf("") }

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
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
//            isError = checkIfFieldIsEmpty  contactName.isBlank()
        )

        OutlinedTextField(
            value = contactNumber,
            onValueChange = { contactNumber = it },
            label = { Text("Contact Number") },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )

        Row {

            //Buttons
            Button(
                onClick = {
                    if (contactName.isNotBlank() && contactNumber.isNotBlank()) {
                        val success = addContact(context, contactName, contactNumber)
                        if (success) {
                            // Clear input fields after successful addition
                            contactName = ""
                            contactNumber = ""
                            contacts = fetchContacts(context)
                        } else {
                            Toast.makeText(context, "An error occurred while adding contact", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
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

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Contacts") },
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )

        // Filtered contacts based on search query
        val filteredContacts = contacts.filter {
            it.contains(searchQuery, ignoreCase = true)
        }

        //List of contacts
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredContacts) { contact ->
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
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
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

fun addContact(context: ComponentActivity, customerName: String, customerNumber: String): Boolean {
    val ops = ArrayList<ContentProviderOperation>()

    ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
        .build())

    // Contact Name
    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, customerName)
        .build())

    // Phone Number
    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, customerNumber)
        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
        .build())

    try {
        context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        Toast.makeText(context, "$customerName has been added to your contacts", Toast.LENGTH_SHORT).show()
        return true
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to add $customerName to your contacts", Toast.LENGTH_SHORT).show()
        return false
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MAPD721A2NkemjikaObiTheme {
        ContactManager(context = ComponentActivity())
    }
}