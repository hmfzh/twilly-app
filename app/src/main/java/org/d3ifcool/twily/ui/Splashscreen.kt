package org.d3ifcool.twily.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.d3ifcool.twily.R


class Splashscreen : AppCompatActivity() {
    private val loading = 3500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HalamanUtama::class.java)
            startActivity(intent)
            finish()
        }, loading.toLong())
    }
}