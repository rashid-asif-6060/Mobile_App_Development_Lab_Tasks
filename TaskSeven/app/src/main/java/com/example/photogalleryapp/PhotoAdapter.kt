package com.example.photogalleryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class PhotoAdapter(private val context: Context, private var photoList: MutableList<Photo>) : BaseAdapter() {
    var isSelectionMode = false

    override fun getCount() = photoList.size
    override fun getItem(position: Int) = photoList[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context)
                .inflate(R.layout.item_photo, parent, false)

            holder = ViewHolder()
            holder.image = view.findViewById(R.id.photoImage)
            holder.title = view.findViewById(R.id.photoTitle)
            holder.checkBox = view.findViewById(R.id.checkBox)

            view.tag = holder

        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val photo = photoList[position]

        holder.image!!.setImageResource(photo.resourceId)
        holder.title!!.text = photo.title

        holder.checkBox!!.visibility =
            if (isSelectionMode) View.VISIBLE else View.GONE

        holder.checkBox!!.isChecked = photo.isSelected

        return view
    }

    class ViewHolder {
        var image: ImageView? = null
        var title: TextView? = null
        var checkBox: CheckBox? = null
    }

    fun updateList(newList: MutableList<Photo>) {
        photoList.clear()
        photoList.addAll(newList)
        notifyDataSetChanged()
    }

    fun deleteSelected() {
        photoList.removeAll { it.isSelected }
        notifyDataSetChanged()
    }

    fun getSelectedCount(): Int {
        return photoList.count { it.isSelected }
    }
}