package org.d3ifcool.twily.util

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object Date {

    fun showDatePicker(context : Context,onDateSetListener: DatePickerDialog.OnDateSetListener){
        //GET Date
        val c = Calendar.getInstance()
        val tahun = c.get(Calendar.YEAR)
        val bulan = c.get(Calendar.MONTH)
        val hari = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, onDateSetListener,tahun,bulan,hari).show()
    }

    fun dateFormatSql(tahun:Int,bulan:Int,hari:Int) : String{
        return "$tahun-$bulan-$hari"
    }

    fun dateFromSqlDate(valDate:String) : String{
        var result = ""
        val date = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).parse(valDate)

        if (date != null){
            result = SimpleDateFormat("EEE,dd-MM-yyyy",Locale.getDefault()).format(date)
        }
        return result
    }
}