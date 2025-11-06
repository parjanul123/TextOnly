package com.example.textonly

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ChatActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var fabAddChat: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // Inițializează elementele din layout
        recyclerView = findViewById(R.id.recyclerChats)
        emptyView = findViewById(R.id.txtEmpty)
        fabAddChat = findViewById(R.id.fabAddChat)

        // Verifică dacă aplicația are permisiunea de a citi contactele
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Cere permisiunea de la utilizator
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                1
            )
        } else {
            // Dacă permisiunea este deja acordată, încarcă contactele
            loadContacts()
        }

        // Butonul pentru chat nou (momentan doar un mesaj)
        fabAddChat.setOnClickListener {
            Toast.makeText(this, "Începeți o conversație nouă!", Toast.LENGTH_SHORT).show()
        }
    }

    // Rezultatul cererii de permisiune
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Dacă utilizatorul a permis accesul → încarcă contactele
            loadContacts()
        } else {
            Toast.makeText(this, "Nu ai permis accesul la contacte!", Toast.LENGTH_SHORT).show()
        }
    }

    // Funcția care citește contactele din telefon și le afișează în listă
    private fun loadContacts() {
        val contacts = ContactsHelper.getContacts(this)

        if (contacts.isEmpty()) {
            // Dacă nu există contacte, ascunde lista și afișează mesajul
            recyclerView.visibility = RecyclerView.GONE
            emptyView.visibility = TextView.VISIBLE
            emptyView.text = "Nu s-au găsit contacte în telefon."
        } else {
            // Afișează lista contactelor
            recyclerView.visibility = RecyclerView.VISIBLE
            emptyView.visibility = TextView.GONE
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = ChatListAdapter(contacts)
        }
    }
}
