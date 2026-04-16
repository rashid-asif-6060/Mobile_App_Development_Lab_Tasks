package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    lateinit var gridView: GridView
    lateinit var adapter: PhotoAdapter
    lateinit var selectionToolBar: LinearLayout
    lateinit var selectedCount: TextView

    var photoList = mutableListOf<Photo>()
    var fullList = mutableListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        gridView = findViewById<GridView>(R.id.gridView)
        selectionToolBar = findViewById<LinearLayout>(R.id.selectionToolBar)
        selectedCount = findViewById<TextView>(R.id.selectedCount)

        loadPhotos()
        adapter = PhotoAdapter(this, photoList)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            val photo = photoList[position]

            if(!adapter.isSelectionMode) {
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image", photo.resourceId)
                startActivity(intent)
            } else {
                photo.isSelected = !photo.isSelected
                updateSelection()
            }
        }

        gridView.setOnItemLongClickListener{ _, _, position, _ ->
            adapter.isSelectionMode = true
            photoList[position].isSelected = true
            selectionToolBar.visibility = View.VISIBLE
            updateSelection()
            true
        }

        findViewById<Button>(R.id.deleteBtn).setOnClickListener {
            val count = adapter.getSelectedCount()
            adapter.deleteSelected()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
            exitSelectionMode()
        }

        findViewById<Button>(R.id.shareBtn).setOnClickListener {
            val count = adapter.getSelectedCount()
            Toast.makeText(this, "$count photos shared", Toast.LENGTH_SHORT).show()
            exitSelectionMode()
        }

        findViewById<Button>(R.id.allTab).setOnClickListener { filter("All") }
        findViewById<Button>(R.id.natureTab).setOnClickListener { filter("Nature") }
        findViewById<Button>(R.id.cityTab).setOnClickListener { filter("City") }
        findViewById<Button>(R.id.animelsTab).setOnClickListener { filter("Animals") }
        findViewById<Button>(R.id.foodTab).setOnClickListener { filter("Food") }
        findViewById<Button>(R.id.travelTab).setOnClickListener { filter("Travel") }
    }

    private fun updateSelection() {
        selectedCount.text = "${adapter.getSelectedCount()} selected"
        adapter.notifyDataSetChanged()
    }

    private fun exitSelectionMode() {
        adapter.isSelectionMode = false
        selectionToolBar.visibility = View.GONE
        photoList.forEach { it.isSelected = false }
        adapter.notifyDataSetChanged()
    }

    private fun filter(category: String) {
        if (category == "All") {
            adapter.updateList(fullList)
        } else {
            val filtered = fullList.filter { it.category == category }.toMutableList()
            adapter.updateList(filtered)
        }
    }

    private fun loadPhotos() {
        fullList.add(Photo(1, R.drawable.pictureone, "Nature 1", "Nature"))
        fullList.add(Photo(2, R.drawable.picturetwo, "Nature 2", "Nature"))
        fullList.add(Photo(3, R.drawable.picturethree, "Nature 3", "Nature"))
        fullList.add(Photo(4, R.drawable.picturefour, "City 1", "City"))
        fullList.add(Photo(5, R.drawable.picturefive, "City 2", "City"))
        fullList.add(Photo(6, R.drawable.picturesix, "Animal 1", "Animals"))
        fullList.add(Photo(7, R.drawable.pictureseven, "Animal 2", "Animals"))
        fullList.add(Photo(8, R.drawable.pictureeight, "Food 1", "Food"))
        fullList.add(Photo(9, R.drawable.picturenine, "Food 2", "Food"))
        fullList.add(Photo(10, R.drawable.pictureten, "Travel 1", "Travel"))
        fullList.add(Photo(11, R.drawable.pictureeleven, "Travel 2", "Travel"))
        fullList.add(Photo(12, R.drawable.picturetwelve, "Travel 3", "Travel"))

        photoList.addAll(fullList)
    }
}