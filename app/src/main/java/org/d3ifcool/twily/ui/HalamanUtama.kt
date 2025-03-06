package org.d3ifcool.twily.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_form_note.*
import kotlinx.android.synthetic.main.activity_halaman_utama.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.twily.adapter.NoteAdapter
import org.d3ifcool.twily.databinding.ActivityHalamanUtamaBinding
import org.d3ifcool.twily.db.Note
import org.d3ifcool.twily.db.NoteDB
import org.d3ifcool.twily.util.Constant

class HalamanUtama : AppCompatActivity() {

    private lateinit var binding: ActivityHalamanUtamaBinding

    val db by lazy { NoteDB(this) }

    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHalamanUtamaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        binding.add.setOnClickListener {
            intentEdit(Constant.TYPE_CREATE, 0)
        }
        setRecyler()
    }
    private fun setRecyler() {
            noteAdapter = NoteAdapter(
                arrayListOf(),
                object : NoteAdapter.OnAdapterListener {

                    override fun onDelete(note: Note) {
                        deleteAlert(note)
                   }

                    override fun onUpdate(note: Note) {
                        intentEdit(Constant.TYPE_UPDATE, note.id,)
                    }
                })

            recylcerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = noteAdapter
            }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            noteAdapter.setData(db.noteDao().getNotes())
            withContext(Dispatchers.Main) {
                noteAdapter.notifyDataSetChanged()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val Todos = db.noteDao().getNotes()
            withContext(Dispatchers.Main) {
                noteAdapter.setData(Todos)
                binding.emptyView.visibility = if (Todos.isEmpty()) View.VISIBLE
                else View.GONE
            }
        }

    }

    private fun deleteAlert(note: Note){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus ${note.title}?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().deleteNote(note)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }

    private fun intentEdit(intent_type: Int, note_id: Int) {
        startActivity(
            Intent(this, FormNoteActivity::class.java)
                .putExtra("intent_type", intent_type)
                .putExtra("note_id", note_id)
        )

    }
}