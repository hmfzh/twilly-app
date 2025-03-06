package org.d3ifcool.twily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.d3ifcool.twily.databinding.ActivityHalamanUtamaBinding
import org.d3ifcool.twily.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}