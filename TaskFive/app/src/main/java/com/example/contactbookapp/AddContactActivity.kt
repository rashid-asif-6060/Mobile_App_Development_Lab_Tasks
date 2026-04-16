package com.example.contactbookapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val phone = phoneInput.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val intent = Intent()
                intent.putExtra("name", name)
                intent.putExtra("phone", phone)

                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}