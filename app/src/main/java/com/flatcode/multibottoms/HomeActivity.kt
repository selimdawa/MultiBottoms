package com.flatcode.multibottoms

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.flatcode.multibottoms.databinding.ActivityHomeBinding
import com.flatcode.multibottoms.new1.MainActivity

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = Intent(this, com.flatcode.multibottoms.MainActivity::class.java)
        val intent2 = Intent(this, MainActivity::class.java)
        val intent3 = Intent(this, com.flatcode.multibottoms.new2.MainActivity::class.java)
        val intent4 = Intent(this, com.flatcode.multibottoms.new3.MainActivity::class.java)

        binding.button1.setOnClickListener { startActivity(intent) }
        binding.button2.setOnClickListener { startActivity(intent2) }
        binding.button3.setOnClickListener { startActivity(intent3) }
        binding.button4.setOnClickListener { startActivity(intent4) }
        binding.button5.setOnClickListener { startActivity(intent) }
        binding.button6.setOnClickListener { startActivity(intent) }
    }
}