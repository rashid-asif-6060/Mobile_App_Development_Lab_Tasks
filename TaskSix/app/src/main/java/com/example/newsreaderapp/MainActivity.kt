package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var scrollView: NestedScrollView

    lateinit var sectionIntro: TextView
    lateinit var sectionKey: TextView
    lateinit var sectionAnalysis: TextView
    lateinit var sectionConclusion: TextView

    lateinit var btnBookMark: ImageButton
    lateinit var btnShare: ImageButton

    var isBookMarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        scrollView = findViewById(R.id.scrollView)
        sectionIntro = findViewById(R.id.sectionIntro)
        sectionKey = findViewById(R.id.sectionKey)
        sectionAnalysis = findViewById(R.id.sectionAnalysis)
        sectionConclusion = findViewById(R.id.sectionConclusion)

        val btnIntro = findViewById<Button>(R.id.introBtn)
        val btnKey = findViewById<Button>(R.id.keyBtn)
        val btnAnalysis = findViewById<Button>(R.id.analysisBtn)
        val btnConclusion = findViewById<Button>(R.id.conclusionBtn)
        val btnTop = findViewById<FloatingActionButton>(R.id.btnTop)

        btnBookMark = findViewById(R.id.bookMarkBtn)
        btnShare = findViewById(R.id.shareBtn)


        btnIntro.setOnClickListener {
            scrollView.smoothScrollTo(0, sectionIntro.top)
        }

        btnKey.setOnClickListener {
            scrollView.smoothScrollTo(0, sectionKey.top)
        }

        btnAnalysis.setOnClickListener {
            scrollView.smoothScrollTo(0, sectionAnalysis.top)
        }

        btnConclusion.setOnClickListener {
            scrollView.smoothScrollTo(0, sectionConclusion.top)
        }


        btnTop.setOnClickListener {
            scrollView.smoothScrollTo(0, 0)
        }

        btnBookMark.setOnClickListener {
            isBookMarked = !isBookMarked

            if (isBookMarked) {
                btnBookMark.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                btnBookMark.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        btnShare.setOnClickListener {
            Toast.makeText(this, "Article Shared", Toast.LENGTH_SHORT).show()
        }
    }
}