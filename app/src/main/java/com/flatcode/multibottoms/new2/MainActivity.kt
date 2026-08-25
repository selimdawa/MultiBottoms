package com.flatcode.multibottoms.new2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flatcode.multibottoms.databinding.ActivityMain3Binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openTopNavigationBar.setOnClickListener {
            launchTopBarActivity()
        }

        binding.openTopFloatNavigationBar.setOnClickListener {
            launchFloatingBarActivity()
        }

        binding.openBottomEqualNavigationBar.setOnClickListener {
            launchEqualBarActivity()
        }

        binding.openBottomNavigationBar.setOnClickListener {
            launchBottomBarActivity()
        }
    }

    private fun launchBottomBarActivity() {
        val intent = Intent(this, BottomBarActivity::class.java)
        startActivity(intent)
    }

    private fun launchFloatingBarActivity() {
        val intent = Intent(this, FloatingTopBarActivity::class.java)
        startActivity(intent)
    }

    private fun launchTopBarActivity() {
        val intent = Intent(this, TopBarActivity::class.java)
        startActivity(intent)
    }

    private fun launchEqualBarActivity() {
        val intent = Intent(this, EqualBottomBarActivity::class.java)
        startActivity(intent)
    }
}
