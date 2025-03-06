package org.d3ifcool.twily.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_form_note.view.*
import kotlinx.android.synthetic.main.item_view.view.*
import org.d3ifcool.twily.R
import org.d3ifcool.twily.db.Note

class NoteAdapter(private val notes:ArrayList<Note>,var listener: OnAdapterListener) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_view,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.view.tv_title.text = note.title
        holder.view.tv_description.text = note.details
        holder.view.tv_date.text = note.date
        holder.view.delete.setOnClickListener {
            listener.onDelete(note)
        }
        holder.view.update.setOnClickListener {
            listener.onUpdate(note)
        }
    }

    override fun getItemCount() = notes.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list : List<Note>){
        notes.clear()
        notes.addAll(list)
    }


    interface OnAdapterListener {
        fun onDelete(note: Note)
        fun onUpdate(note: Note)
    }
}