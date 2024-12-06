package com.example.cozydesks

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resources = resources.getStringArray(R.array.countries)
        val arrayAdapter = ArrayAdapter(this,R.layout.dropdown_item, resources)


        var button: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView)
        button.setAdapter(arrayAdapter)
        button.setOnClickListener {
            val intent = Intent(this,test::class.java)
            startActivity(intent)
        }


    }
}