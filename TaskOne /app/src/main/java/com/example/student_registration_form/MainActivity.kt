package com.example.student_registration_form

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val studentID = findViewById<EditText>(R.id.stdIDInput)
        val fullName = findViewById<EditText>(R.id.fullNameInput)
        val email = findViewById<EditText>(R.id.emailInput)
        val password = findViewById<EditText>(R.id.passwordInput)
        val age = findViewById<EditText>(R.id.ageInput)
        val gender = findViewById<RadioGroup>(R.id.genderGroup)
        val favSport = findViewById<LinearLayout>(R.id.sportsGroup)
        val country = findViewById<Spinner>(R.id.countrySelectionSpinner)
        val dobInput = findViewById<EditText>(R.id.dobInput)

        dobInput.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dobInput.setText(date)}, year, month, day)
            datePickerDialog.show()
        }

        val submitBtn = findViewById<Button>(R.id.submitButton)
        val resetBtn = findViewById<Button>(R.id.resetButton)

        resetBtn.setOnClickListener{
            studentID.setText("")
            fullName.setText("")
            email.setText("")
            password.setText("")
            age.setText("")
            gender.clearCheck()

            for(i in 0 until favSport.childCount){
                (favSport.getChildAt(i) as CheckBox).isChecked = false
            }

            country.setSelection(0)
            dobInput.setText("")
        }

        submitBtn.setOnClickListener {
            val id = studentID.text.toString()
            val name = fullName.text.toString()
            val emailValue = email.text.toString()
            val passwordValue = password.text.toString()
            val ageValue = age.text.toString()
            val birth = dobInput.text.toString()

            val genderId = gender.checkedRadioButtonId

            val sports = mutableListOf<String>()
            for (i in 0 until favSport.childCount) {
                val view = favSport.getChildAt(i)
                if (view is CheckBox && view.isChecked) {
                    sports.add(view.text.toString())
                }
            }

            val sportsText = if (sports.isEmpty()) "None" else sports.joinToString(", ")

            if (id.isEmpty() || name.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty()
                || ageValue.isEmpty() || birth.isEmpty()) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(ageValue.toInt() < 0) {
                Toast.makeText(this, "Age must be greater than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(!emailValue.endsWith("@gmail.com")) {
                Toast.makeText(this, "Email must end with @gmail.com", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (genderId == -1) {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(country.selectedItemPosition == 0) {
                Toast.makeText(this, "Please select country", Toast.LENGTH_SHORT).show()
            }

            val genderText = findViewById<RadioButton>(genderId).text.toString()

            val message = "ID: $id\nName: $name\nEmail: $emailValue\nPassword: $passwordValue\nAge: $ageValue\nGender: $genderText\nSports: $sportsText\nCountry: ${country.selectedItem}\nBirth: $birth"

            AlertDialog.Builder(this).setTitle("Student Details").setMessage(message).setPositiveButton("OK", null).show()
        }

    }
}