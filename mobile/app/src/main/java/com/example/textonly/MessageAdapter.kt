package text.only.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(private val messages: MutableList<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // Constante pentru tipurile de view-uri
    companion object {
        private const val TYPE_TEXT_SENT = 0
        private const val TYPE_TEXT_RECEIVED = 1
        private const val TYPE_FILE_SENT = 2
        private const val TYPE_FILE_RECEIVED = 3
    }

    // View Holder pentru mesaje text (simplificat)
    class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textMessage: TextView = view.findViewById(R.id.textMessage)
    }

    // View Holder pentru fișiere
    class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtFileName: TextView = view.findViewById(R.id.txtFileName)
        val txtPrice: TextView? = view.findViewById(R.id.txtPrice) // Poate fi null la received
        val btnUnlock: Button? = view.findViewById(R.id.btnUnlock) // Poate fi null la sent
    }

    override fun getItemViewType(position: Int): Int {
        return when (val message = messages[position]) {
            is TextMessage -> if (message.isSent) TYPE_TEXT_SENT else TYPE_TEXT_RECEIVED
            is FileMessage -> if (message.isSent) TYPE_FILE_SENT else TYPE_FILE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TEXT_SENT -> TextViewHolder(inflater.inflate(R.layout.item_message, parent, false)) // TODO: Creează item_message_sent
            TYPE_TEXT_RECEIVED -> TextViewHolder(inflater.inflate(R.layout.item_message, parent, false)) // TODO: Creează item_message_received
            TYPE_FILE_SENT -> FileViewHolder(inflater.inflate(R.layout.item_message_file_sent, parent, false))
            TYPE_FILE_RECEIVED -> FileViewHolder(inflater.inflate(R.layout.item_message_file_received, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val message = messages[position]) {
            is TextMessage -> {
                (holder as TextViewHolder).textMessage.text = message.content
            }
            is FileMessage -> {
                val fileHolder = holder as FileViewHolder
                fileHolder.txtFileName.text = message.fileName
                
                if (message.isSent) {
                    fileHolder.txtPrice?.text = "Preț: ${message.price} OnlyCoins"
                } else {
                    if (message.isUnlocked) {
                        fileHolder.btnUnlock?.text = "Descarcă"
                    } else {
                        fileHolder.btnUnlock?.text = "Deblochează (${message.price} OnlyCoins)"
                    }
                    
                    fileHolder.btnUnlock?.setOnClickListener {
                        if (message.isUnlocked) {
                            // TODO: Logica de descărcare a fișierului de la message.remoteUrl
                            Toast.makeText(it.context, "Se descarcă...", Toast.LENGTH_SHORT).show()
                        } else {
                            // TODO: Logica de plată cu OnlyCoins
                            // Dacă plata are succes:
                            message.isUnlocked = true
                            notifyItemChanged(position) // Re-desenează acest item ca "deblocat"
                            Toast.makeText(it.context, "Deblocat!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = messages.size
}
