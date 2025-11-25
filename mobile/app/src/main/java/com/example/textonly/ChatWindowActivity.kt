package com.example.textonly

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatWindowActivity : AppCompatActivity() {

    private lateinit var txtContactName: TextView
    private lateinit var recyclerMessages: RecyclerView
    private lateinit var inputMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var btnCall: ImageButton
    private lateinit var btnVideoCall: ImageButton
    private lateinit var btnDetails: ImageButton

    private val messages = mutableListOf<String>()
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        txtContactName = findViewById(R.id.txtContactName)
        recyclerMessages = findViewById(R.id.recyclerMessages)
        inputMessage = findViewById(R.id.inputMessage)
        btnSend = findViewById(R.id.btnSend)
        btnCall = findViewById(R.id.btnCall)
        btnVideoCall = findViewById(R.id.btnVideoCall)
        btnDetails = findViewById(R.id.btnDetails)

        val contactName = intent.getStringExtra("contact_name")
        val contactPhone = intent.getStringExtra("contact_phone")
        txtContactName.text = contactName

        adapter = MessageAdapter(messages)
        recyclerMessages.layoutManager = LinearLayoutManager(this)
        recyclerMessages.adapter = adapter

        // buton trimitere mesaj
        btnSend.setOnClickListener {
            val text = inputMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                messages.add("Tu: $text")
                adapter.notifyItemInserted(messages.size - 1)
                recyclerMessages.scrollToPosition(messages.size - 1)
                inputMessage.text.clear()
            }
        }

        // 📞 Apel vocal → deschide CallActivity
        btnCall.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("contact_name", contactName)
            intent.putExtra("call_type", "audio")
            startActivity(intent)
        }

        // 🎥 Apel video → deschide CallActivity
        btnVideoCall.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            intent.putExtra("contact_name", contactName)
            intent.putExtra("call_type", "video")
            startActivity(intent)
        }

        // ℹ️ Detalii contact
        btnDetails.setOnClickListener {
            Toast.makeText(this, "Detalii: $contactName - $contactPhone", Toast.LENGTH_LONG).show()
        }
    }
}
