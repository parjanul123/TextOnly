package text.only.app

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ChatFragment : Fragment() {

    private lateinit var recyclerMessages: RecyclerView
    private lateinit var inputMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var btnAttach: ImageButton
    private lateinit var messageAdapter: MessageAdapter
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var contextId: String // ID-ul unic pentru această conversație/canal

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { showSetPriceDialog(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contextId = arguments?.getString("CONTEXT_ID") ?: "default_context"
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerMessages = view.findViewById(R.id.recycler_chat_messages)
        inputMessage = view.findViewById(R.id.input_chat_message)
        btnSend = view.findViewById(R.id.btn_send_message)
        btnAttach = view.findViewById(R.id.btn_attach_file)

        setupRecyclerView()
        loadMessagesFromDb()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        messageAdapter = MessageAdapter(chatMessages, requireActivity() as MessageInteractionListener)
        recyclerMessages.layoutManager = LinearLayoutManager(context)
        recyclerMessages.adapter = messageAdapter
    }
    
    private fun loadMessagesFromDb() {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(requireContext())
            val messages = db.messageDao().getMessagesForContext(contextId)
            chatMessages.clear()
            chatMessages.addAll(messages.map { TextMessage(it.content, it.isSent) }) // Momentan, convertim doar mesaje text
            messageAdapter.notifyDataSetChanged()
            recyclerMessages.scrollToPosition(chatMessages.size - 1)
        }
    }

    private fun setupClickListeners() {
        btnAttach.setOnClickListener { filePickerLauncher.launch("*/*") }

        btnSend.setOnClickListener {
            val messageText = inputMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                lifecycleScope.launch {
                    val newMessage = MessageEntity(contextId = contextId, content = messageText, isSent = true)
                    AppDatabase.getInstance(requireContext()).messageDao().insert(newMessage)
                    loadMessagesFromDb() // Reîncărcăm totul din DB pentru a afișa noul mesaj
                    inputMessage.text.clear()
                }
            }
        }
    }

    // ... (celelalte funcții: showSetPriceDialog, sendFile, getFileName) rămân la fel ...
    private fun showSetPriceDialog(fileUri: Uri) { /*...*/ }
    private fun sendFile(fileUri: Uri, price: Int) { /*...*/ }
    private fun getFileName(uri: Uri): String { /*...*/ return "" }
    
    companion object {
        fun newInstance(contextId: String): ChatFragment {
            val fragment = ChatFragment()
            val args = Bundle()
            args.putString("CONTEXT_ID", contextId)
            fragment.arguments = args
            return fragment
        }
    }
}
