package text.only.app

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import text.only.app.qrlogin.ToolbarMenuHandler

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerChats: RecyclerView
    private lateinit var txtEmpty: TextView
    private lateinit var searchBar: EditText
    private lateinit var fabAddChat: FloatingActionButton
    private lateinit var btnSettings: ImageButton

    private lateinit var adapter: ChatListAdapter
    private var chatList = mutableListOf<Contact>() // Aici vin DOAR conversațiile începute

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerChats = findViewById(R.id.recyclerChats)
        txtEmpty = findViewById(R.id.txtEmpty)
        searchBar = findViewById(R.id.searchBar)
        fabAddChat = findViewById(R.id.fabAddChat)
        btnSettings = findViewById(R.id.btnSettings)

        ToolbarMenuHandler.setupToolbar(this, null, btnSettings)

        recyclerChats.layoutManager = LinearLayoutManager(this)
        adapter = ChatListAdapter(chatList) // Inițializăm cu lista goală
        recyclerChats.adapter = adapter

        fabAddChat.setOnClickListener {
            // Deschide ecranul de selecție a contactelor
            startActivity(Intent(this, SelectContactActivity::class.java))
        }
    }
}