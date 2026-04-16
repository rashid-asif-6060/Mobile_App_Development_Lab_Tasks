package com.example.contactbookapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView

class ContactAdapter(
    context: Context,
    private val contacts: MutableList<Contact>
) : ArrayAdapter<Contact>(context, 0, contacts) {

    private var filteredList: MutableList<Contact> = contacts.toMutableList()

    override fun getCount(): Int = filteredList.size

    override fun getItem(position: Int): Contact {
        return filteredList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_row, parent, false)

        val contact = getItem(position)

        val avatar = view.findViewById<TextView>(R.id.avatar)
        val name = view.findViewById<TextView>(R.id.name)
        val phone = view.findViewById<TextView>(R.id.phone)

        avatar.text = contact.initial
        name.text = contact.name
        phone.text = contact.phone

        return view
    }

    fun updateList(newList: MutableList<Contact>) {
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val result = FilterResults()
                val query = constraint.toString().lowercase()

                result.values = if (query.isEmpty()) {
                    contacts.toList()
                } else {
                    contacts.filter {
                        it.name.lowercase().contains(query)
                    }
                }

                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = (results?.values as List<Contact>).toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}