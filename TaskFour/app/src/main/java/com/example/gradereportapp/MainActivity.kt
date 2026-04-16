package com.example.gradereportapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var tableLayout: TableLayout
    lateinit var subjectInput: EditText
    lateinit var obtainedInput: EditText
    lateinit var totalInput: EditText
    lateinit var addBtn: Button
    lateinit var gpaText: TextView
    lateinit var summaryText: TextView

    var totalSubjects = 7
    var passedSubjects = 7
    var failedSubjects = 0
    var GPA = 3.78
    var totalGradePoints = GPA * totalSubjects


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        subjectInput = findViewById(R.id.subjectInput)
        obtainedInput = findViewById(R.id.obtainedMarkInput)
        totalInput = findViewById(R.id.totalMarkInput)
        addBtn = findViewById(R.id.addBtn)
        gpaText = findViewById(R.id.displayGPA)
        summaryText = findViewById(R.id.tableSummary)

        fun calculateGrade(marks: Int): Pair<String, Double> {
            return when {
                marks >= 90 -> Pair("A+", 4.00)
                marks >= 80 -> Pair("A", 3.5)
                marks >= 70 -> Pair("B+", 3.0)
                marks >= 60 -> Pair("B", 2.5)
                marks >= 50 -> Pair("C", 2.0)
                marks >= 40 -> Pair("D", 1.5)
                else -> Pair("F", 0.0)
            }
        }

        addBtn.setOnClickListener {
            val subject = subjectInput.text.toString()
            val obtainedMark = obtainedInput.text.toString()
            val totalMark = totalInput.text.toString()

            if(subject.isEmpty() || obtainedMark.isEmpty() || totalMark.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obtained = obtainedMark.toInt()
            val total = totalMark.toInt()

            if(total == 0) {
                Toast.makeText(this, "Total mark cannot be zero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val (grade, gradePoint) = calculateGrade(obtained)

            val row = TableRow(this)
            row.setBackgroundColor(Color.parseColor("#CCCCCC"))

            fun createTextView(text: String): TextView {
                val tv = TextView(this)
                tv.text = text
                tv.textSize = 15f
                tv.setTextColor(Color.BLACK)
                tv.setPadding(4, 4, 4, 4)
                tv.gravity = Gravity.CENTER
                return tv
            }

            row.addView(createTextView(subject))
            row.addView(createTextView(obtained.toString()))
            row.addView(createTextView(total.toString()))
            row.addView(createTextView(grade))

            tableLayout.addView(row, tableLayout.childCount - 1)

            totalSubjects++
            if (gradePoint > 0) {
                passedSubjects++
            } else {
                failedSubjects++
            }

            totalGradePoints += gradePoint

            summaryText.text = "Total: $totalSubjects | Passed: $passedSubjects | Failed: $failedSubjects"
            val gpa = totalGradePoints / totalSubjects
            gpaText.text = "GPA: %.2f".format(gpa)

            subjectInput.text.clear()
            obtainedInput.text.clear()
            totalInput.text.clear()
        }
    }
}