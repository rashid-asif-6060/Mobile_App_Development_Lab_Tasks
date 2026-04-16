package com.example.contactbookapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchView: SearchView
    private lateinit var fab: FloatingActionButton
    private lateinit var emptyView: TextView

    private lateinit var adapter: ContactAdapter
    private var contactList = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        searchView = findViewById(R.id.searchView)
        fab = findViewById(R.id.fab)
        emptyView = findViewById(R.id.errorForNoContact)

        adapter = ContactAdapter(this, contactList)
        listView.adapter = adapter

        updateEmptyView()

        fab.setOnClickListener {
            val intent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(intent, 1)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)

            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtra("name", contact.name)
            intent.putExtra("phone", contact.phone)
            intent.putExtra("email", contact.email)
            intent.putExtra("initial", contact.initial)

            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)
            contactList.remove(contact)

            adapter.updateList(contactList) // 🔥 FIX HERE
            updateEmptyView()
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                listView.post { updateEmptyView() }
                return true
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val name = data?.getStringExtra("name") ?: return
            val phone = data.getStringExtra("phone") ?: return

            val initial = name
                .split(" ")
                .take(2)
                .joinToString("") { it[0].uppercase() }

            val contact = Contact(name, phone, "no-email@gmail.com", initial)

            contactList.add(contact)

            adapter.updateList(contactList) // 🔥 FIX HERE
            updateEmptyView()
        }
    }

    private fun updateEmptyView() {
        emptyView.visibility =
            if (adapter.count == 0) View.VISIBLE else View.GONE
    }
}