package text.only.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface MessageInteractionListener {
    fun onUnlockFileRequested(message: FileMessage, position: Int)
    fun onInviteAction(inviteCode: String, accepted: Boolean, position: Int)
}

class MessageAdapter(
    private val messages: MutableList<ChatMessage>,
    private val listener: MessageInteractionListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TEXT_SENT = 0
        private const val TYPE_TEXT_RECEIVED = 1
        private const val TYPE_FILE_SENT = 2
        private const val TYPE_FILE_RECEIVED = 3
        private const val TYPE_INVITE_RECEIVED = 4
    }

    class TextViewHolder(v: View) : RecyclerView.ViewHolder(v) { val txt: TextView = v.findViewById(R.id.textMessage) }
    class FileViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val fileName: TextView = v.findViewById(R.id.txtFileName)
        val price: TextView? = v.findViewById(R.id.txtPrice)
        val unlock: Button? = v.findViewById(R.id.btnUnlock)
    }
    class InviteViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val serverName: TextView = v.findViewById(R.id.txtServerName)
        val inviterName: TextView = v.findViewById(R.id.txtInviterName)
        val btnAccept: Button = v.findViewById(R.id.btnAccept)
        val btnRefuse: Button = v.findViewById(R.id.btnRefuse)
    }

    override fun getItemViewType(position: Int): Int {
        return when (val msg = messages[position]) {
            is TextMessage -> if (msg.isSent) TYPE_TEXT_SENT else TYPE_TEXT_RECEIVED
            is FileMessage -> if (msg.isSent) TYPE_FILE_SENT else TYPE_FILE_RECEIVED
            is InviteMessage -> TYPE_INVITE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TEXT_SENT -> TextViewHolder(inflater.inflate(R.layout.item_message, parent, false))
            TYPE_TEXT_RECEIVED -> TextViewHolder(inflater.inflate(R.layout.item_message, parent, false))
            TYPE_FILE_SENT -> FileViewHolder(inflater.inflate(R.layout.item_message_file_sent, parent, false))
            TYPE_FILE_RECEIVED -> FileViewHolder(inflater.inflate(R.layout.item_message_file_received, parent, false))
            TYPE_INVITE_RECEIVED -> InviteViewHolder(inflater.inflate(R.layout.item_message_invite_received, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val message = messages[position]) {
            is TextMessage -> {
                val textHolder = holder as TextViewHolder
                textHolder.txt.text = message.content
            }
            is FileMessage -> {
                val fileHolder = holder as FileViewHolder
                fileHolder.fileName.text = message.fileName
                if (message.isSent) {
                    fileHolder.price?.text = "Preț: ${message.price} OnlyCoins"
                } else {
                    fileHolder.unlock?.setOnClickListener { listener.onUnlockFileRequested(message, position) }
                }
            }
            is InviteMessage -> {
                val inviteHolder = holder as InviteViewHolder
                inviteHolder.serverName.text = message.serverName
                inviteHolder.inviterName.text = "Invitat de: ${message.inviterName}"

                when (message.state) {
                    InviteState.PENDING -> {
                        inviteHolder.btnAccept.visibility = View.VISIBLE
                        inviteHolder.btnRefuse.text = "Refuză"
                        inviteHolder.btnRefuse.isEnabled = true
                        inviteHolder.btnAccept.setOnClickListener { listener.onInviteAction(message.inviteCode, true, position) }
                        inviteHolder.btnRefuse.setOnClickListener { listener.onInviteAction(message.inviteCode, false, position) }
                    }
                    InviteState.ACCEPTED -> {
                        inviteHolder.btnAccept.visibility = View.GONE
                        inviteHolder.btnRefuse.text = "Acceptat"
                        inviteHolder.btnRefuse.isEnabled = false
                    }
                    InviteState.REFUSED -> {
                        inviteHolder.btnAccept.visibility = View.GONE
                        inviteHolder.btnRefuse.text = "Refuzat"
                        inviteHolder.btnRefuse.isEnabled = false
                    }
                }
            }
        }
    }

    override fun getItemCount() = messages.size
}
