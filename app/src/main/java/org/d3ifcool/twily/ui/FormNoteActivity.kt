package org.d3ifcool.twily.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3ifcool.twily.R
import org.d3ifcool.twily.databinding.ActivityFormNoteBinding
import org.d3ifcool.twily.db.Note
import org.d3ifcool.twily.db.NoteDB
import org.d3ifcool.twily.util.Constant
import org.d3ifcool.twily.util.Date

class FormNoteActivity : AppCompatActivity() {

    private lateinit var formNoteBiding : ActivityFormNoteBinding
    val db by lazy { NoteDB(this) }
    private var noteId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        formNoteBiding = ActivityFormNoteBinding.inflate(layoutInflater)
        setContentView(formNoteBiding.root)
        supportActionBar!!.hide()
        onClick()
        when (intentType()) {
            Constant.TYPE_CREATE -> {
                formNoteBiding.btnSubmit.visibility = View.VISIBLE
                formNoteBiding.btnEdit.visibility = View.GONE
            }
            Constant.TYPE_UPDATE -> {
                formNoteBiding.btnSubmit.visibility = View.GONE
                formNoteBiding.btnEdit.visibility = View.VISIBLE
                getNote(true)
            }
        }
    }

    private fun onClick() {
        formNoteBiding.btnAddDateTask.setOnClickListener {
            Date.showDatePicker(this
            ) { view, tahun,bulan, hari ->
                val dateString = Date.dateFormatSql(tahun,bulan,hari)
                formNoteBiding.btnAddDateTask.text = Date.dateFromSqlDate(dateString)
                checkIsDateFilled(true)
            }
        }

        formNoteBiding.btnRemoveDateTask.setOnClickListener {
            formNoteBiding.btnAddDateTask.text = null
            checkIsDateFilled(false)
        }

        formNoteBiding.btnSubmit.setOnClickListener {
                if(formNoteBiding.etAddDetailsTask.text.length == 0){
                    formNoteBiding.etAddDetailsTask.setError("Masukan Input Terlebih dahulu")
                }
                else if(formNoteBiding.etAddDetailsTask2.text.length == 0){
                    formNoteBiding.etAddDetailsTask2.setError("Masukan Input Terlebih dahulu")
                }
                else if(formNoteBiding.btnAddDateTask.text.length == 0) {
                    formNoteBiding.btnAddDateTask.setError("Masukan Input Terlebih dahulu")
                }else{
                    Toast.makeText(applicationContext,"Berhasil menambahkan data",Toast.LENGTH_SHORT).show()

                    CoroutineScope(Dispatchers.IO).launch {
                        db.noteDao().addTodo(
                            Note(
                                0,
                                formNoteBiding.etAddDetailsTask.text.toString(),
                                formNoteBiding.etAddDetailsTask2.text.toString(),
                                formNoteBiding.btnAddDateTask.text.toString(),
                            )
                        )
                }
                finish()
            }

        }

        formNoteBiding.btnEdit.setOnClickListener {

            if(formNoteBiding.etAddDetailsTask.text.length == 0){
                formNoteBiding.etAddDetailsTask.setError("Masukan Input Terlebih dahulu")
            }
            else if(formNoteBiding.etAddDetailsTask2.text.length == 0){
                formNoteBiding.etAddDetailsTask2.setError("Masukan Input Terlebih dahulu")
            }
            else if(formNoteBiding.btnAddDateTask.text.length == 0) {
                formNoteBiding.btnAddDateTask.setError("Masukan Input Terlebih dahulu")
            }else{
                Toast.makeText(applicationContext,"Berhasil Edit data",Toast.LENGTH_SHORT).show()

                CoroutineScope(Dispatchers.IO).launch {
                    db.noteDao().updateNote(
                        Note(
                            noteId,
                            formNoteBiding.etAddDetailsTask.text.toString(),
                            formNoteBiding.etAddDetailsTask2.text.toString(),
                            formNoteBiding.btnAddDateTask.text.toString()
                        )
                    )
                }
                finish()
            }

        }

    }

    private fun checkIsDateFilled(isDateFilled: Boolean) {
        if(isDateFilled){
            formNoteBiding.btnAddDateTask.background = ContextCompat.getDrawable(this,
                R.color.cardview_light_background
            )
            formNoteBiding.btnAddDateTask.setPadding(24,24,24,24)
            formNoteBiding.btnRemoveDateTask.visibility = View.VISIBLE
        }else{
            formNoteBiding.btnAddDateTask.setBackgroundResource(0)
            formNoteBiding.btnAddDateTask.setPadding(0,0,0,0)
            formNoteBiding.btnRemoveDateTask.visibility = View.GONE
        }
    }

    private fun intentType(): Int {
        return intent.getIntExtra("intent_type", 0)
    }

    private fun getNote(isDateFilled: Boolean){
        noteId = intent.getIntExtra("note_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.noteDao().getNote(noteId).get(0)
            formNoteBiding.etAddDetailsTask.setText( notes.title )
            formNoteBiding.etAddDetailsTask2.setText( notes.details )
            formNoteBiding.btnAddDateTask.text = notes.date

            if(isDateFilled){
                formNoteBiding.btnAddDateTask.background = ContextCompat.getDrawable(this@FormNoteActivity,
                    R.color.cardview_light_background
                )
                formNoteBiding.btnAddDateTask.setPadding(24,24,24,24)
                formNoteBiding.btnRemoveDateTask.visibility = View.VISIBLE
            }else{
                formNoteBiding.btnAddDateTask.setBackgroundResource(0)
                formNoteBiding.btnAddDateTask.setPadding(0,0,0,0)
                formNoteBiding.btnRemoveDateTask.visibility = View.GONE
            }
        }
    }
}