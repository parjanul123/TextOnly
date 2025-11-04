package com.example.textonly.chat

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.textonly.R
import com.example.textonly.chat.ChatListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView

class ChatActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView
    private lateinit var fabAddChat: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.recyclerChats)
        emptyView = findViewById(R.id.txtEmpty)
        fabAddChat = findViewById(R.id.fabAddChat)

        // Simulăm o listă de chat-uri (temporar goală)
        val chatList = listOf<String>() // în viitor va veni din Firebase

        if (chatList.isEmpty()) {
            recyclerView.visibility = RecyclerView.GONE
            emptyView.visibility = TextView.VISIBLE
        } else {
            recyclerView.visibility = RecyclerView.VISIBLE
            emptyView.visibility = TextView.GONE
        }

        fabAddChat.setOnClickListener {
            Toast.makeText(this, "Începeți o conversație nouă!", Toast.LENGTH_SHORT).show()
            // aici în viitor vei deschide ecranul de selectare contact / chat nou
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatListAdapter(chatList)
    }
}
