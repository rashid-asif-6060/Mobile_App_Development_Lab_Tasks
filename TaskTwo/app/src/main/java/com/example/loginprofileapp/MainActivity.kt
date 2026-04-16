package com.example.loginprofileapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)

        val loginContainer = findViewById<RelativeLayout>(R.id.loginContainer)
        val profileContainer = findViewById<RelativeLayout>(R.id.profileContainer)
        val forgetPassUserNameVerificationContainer = findViewById<RelativeLayout>(R.id.forgetPassUserNameVerificationContainer)
        val forgetCreateNewPassContainer = findViewById<RelativeLayout>(R.id.forgetCreateNewPassContainer)

        val usernameInput = findViewById<EditText>(R.id.usernameInput)
        val passwordInput = findViewById<EditText>(R.id.passworInput)
        val forgetUserNameInput = findViewById<EditText>(R.id.forgetUserNameInput)
        val createNewPassInput = findViewById<EditText>(R.id.createNewPassInput)
        val confirmNewPassInput = findViewById<EditText>(R.id.confirmNewPassInput)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val resetBtn = findViewById<Button>(R.id.resetBtn)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)
        val forgetBtn = findViewById<TextView>(R.id.forgetPassBtn)
        val verifyBtn = findViewById<Button>(R.id.verifyBtn)
        val changeBtn = findViewById<Button>(R.id.changeBtn)

        resetBtn.setOnClickListener {
            usernameInput.text.clear()
            passwordInput.text.clear()
        }

        loginBtn.setOnClickListener {
            val userName = usernameInput.text.toString()
            val pass = passwordInput.text.toString()

            if (userName.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter username & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(userName == "admin" && pass == "1234") {
                progressBar.visibility = android.view.View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({

                    progressBar.visibility = android.view.View.GONE
                    loginContainer.visibility = RelativeLayout.INVISIBLE
                    profileContainer.visibility = RelativeLayout.VISIBLE

                }, 2000)
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        logoutBtn.setOnClickListener {
            progressBar.visibility = android.view.View.VISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = android.view.View.GONE

                usernameInput.text.clear()
                passwordInput.text.clear()

                profileContainer.visibility = RelativeLayout.INVISIBLE
                loginContainer.visibility = RelativeLayout.VISIBLE
            }, 2000)
        }

        forgetBtn.setOnClickListener {
            forgetPassUserNameVerificationContainer.visibility = RelativeLayout.VISIBLE
            loginContainer.visibility = RelativeLayout.INVISIBLE
        }

        verifyBtn.setOnClickListener {
            val userName = forgetUserNameInput.text.toString()

            if(userName.isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(userName == "admin") {
                forgetPassUserNameVerificationContainer.visibility = RelativeLayout.INVISIBLE
                forgetCreateNewPassContainer.visibility = RelativeLayout.VISIBLE
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                forgetUserNameInput.text.clear()
                return@setOnClickListener
            }
        }

        changeBtn.setOnClickListener {
            val newPass = createNewPassInput.text.toString()
            val confirmPass = confirmNewPassInput.text.toString()

            if(newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(newPass != confirmPass) {
                Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()

                progressBar.visibility = android.view.View.VISIBLE

                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = android.view.View.GONE

                    createNewPassInput.text.clear()
                    confirmNewPassInput.text.clear()
                    usernameInput.text.clear()
                    passwordInput.text.clear()

                    forgetCreateNewPassContainer.visibility = RelativeLayout.INVISIBLE
                    loginContainer.visibility = RelativeLayout.VISIBLE
                }, 2000)
            }
        }

    }
}