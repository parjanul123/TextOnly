package text.only.app

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatWindowActivity : AppCompatActivity() {

    private lateinit var btnAttach: ImageButton
    private lateinit var recyclerMessages: RecyclerView
    private lateinit var txtContactName: TextView
    private lateinit var messageAdapter: MessageAdapter
    private val chatMessages = mutableListOf<ChatMessage>()

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { showSetPriceDialog(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)
        
        // Inițializarea view-urilor
        btnAttach = findViewById(R.id.btnAttach)
        recyclerMessages = findViewById(R.id.recyclerMessages)
        txtContactName = findViewById(R.id.txtContactName)
        
        // Preluarea numelui contactului din Intent și afișarea lui
        val contactName = intent.getStringExtra("contact_name")
        txtContactName.text = contactName ?: "Contact" // Folosim "Contact" ca text implicit

        setupRecyclerView()

        btnAttach.setOnClickListener {
            filePickerLauncher.launch("*/*")
        }
        
        // TODO: Adaugă logica pentru trimiterea mesajelor text
    }
    
    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(chatMessages)
        recyclerMessages.layoutManager = LinearLayoutManager(this)
        recyclerMessages.adapter = messageAdapter
    }

    private fun showSetPriceDialog(fileUri: Uri) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Setează Prețul (OnlyCoins)")

        val input = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_NUMBER
            hint = "0 (gratuit)"
        }
        builder.setView(input)

        builder.setPositiveButton("Trimite") { dialog, _ ->
            val price = input.text.toString().toIntOrNull() ?: 0
            sendFile(fileUri, price)
            dialog.dismiss()
        }
        
        builder.setNegativeButton("Anulează") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
    
    private fun getFileName(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (it != null && it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                if (cut != null) {
                    result = result?.substring(cut + 1)
                }
            }
        }
        return result ?: "fișier_necunoscut"
    }

    private fun sendFile(fileUri: Uri, price: Int) {
        val fileName = getFileName(fileUri)
        val fileType = contentResolver.getType(fileUri)

        val fileMessage = FileMessage(
            fileName = fileName,
            fileType = fileType,
            price = price,
            isSent = true,
            isUnlocked = true,
            localUri = fileUri
        )
        
        chatMessages.add(fileMessage)
        messageAdapter.notifyItemInserted(chatMessages.size - 1)
        recyclerMessages.scrollToPosition(chatMessages.size - 1)

        Toast.makeText(this, "Se încarcă fișierul...", Toast.LENGTH_SHORT).show()
        // TODO: Aici adaugi logica de upload pe server și trimitere prin WebSocket
    }
}
