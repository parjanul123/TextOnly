package text.only.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectContactActivity : AppCompatActivity() {

    private lateinit var recyclerContacts: RecyclerView
    private lateinit var searchContacts: EditText
    private lateinit var adapter: ChatListAdapter
    private var allContacts = mutableListOf<Contact>()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                loadContacts()
            } else {
                Toast.makeText(this, "Permisiunea pentru contacte a fost refuzată.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_contact)

        recyclerContacts = findViewById(R.id.recyclerContacts)
        searchContacts = findViewById(R.id.searchContacts)
        recyclerContacts.layoutManager = LinearLayoutManager(this)
        
        adapter = ChatListAdapter(mutableListOf())
        recyclerContacts.adapter = adapter

        checkPermissionsAndLoadContacts()

        searchContacts.addTextChangedListener { text ->
            filter(text.toString())
        }
    }

    private fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            allContacts
        } else {
            allContacts.filter {
                // Modificat aici: folosește startsWith în loc de contains pentru nume
                it.name.startsWith(query, ignoreCase = true) || 
                it.phone.replace(" ", "").contains(query)
            }
        }
        adapter = ChatListAdapter(filteredList)
        recyclerContacts.adapter = adapter
    }

    private fun checkPermissionsAndLoadContacts() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                loadContacts()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun loadContacts() {
        val contactsHelper = ContactsHelper(this)
        val contacts = contactsHelper.getContacts()
        
        if (contacts.isEmpty()) {
            Toast.makeText(this, "Nu am găsit contacte pe acest dispozitiv.", Toast.LENGTH_LONG).show()
        }
        
        allContacts.clear()
        allContacts.addAll(contacts)
        
        adapter = ChatListAdapter(allContacts)
        recyclerContacts.adapter = adapter
    }
}
