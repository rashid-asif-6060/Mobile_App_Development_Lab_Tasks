package com.example.contactbookapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_info)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val initial = intent.getStringExtra("initial")

        val avatar = findViewById<TextView>(R.id.avater)
        val nameView = findViewById<TextView>(R.id.name)
        val phoneView = findViewById<TextView>(R.id.phone)

        avatar.text = initial
        nameView.text = name
        phoneView.text = phone
    }
}