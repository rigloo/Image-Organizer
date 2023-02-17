package com.rigosapps.imageorganizer

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.rigosapps.imageorganizer.helpers.TimeHelper
import com.rigosapps.imageorganizer.viewModels.ImageItem
import timber.log.Timber

class ItemAdapter(
    val items: MutableList<ImageItem>, val onClick: (imageItem: ImageItem) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val imageView: ImageView
        val date: TextView
        val cardView: CardView

        init {

            titleText = view.findViewById(R.id.textViewtitle)
            date = view.findViewById(R.id.textViewDate)
            imageView = view.findViewById(R.id.imageView)
            cardView = view.findViewById(R.id.cardView)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleText.text = items[position].title
        holder.date.text = TimeHelper.getStringfromDate(items[position].date)
        if (items[position].imagePath == "null")
            holder.imageView.setImageResource(R.drawable.no_image)
        else
            holder.imageView.setImageURI(items[position].imagePath!!.toUri())
        holder.cardView.setOnClickListener() {

            Timber.e("Navigate from home to Detail.")
            onClick(items[position])

        }

    }

    override fun getItemCount(): Int {
//        return items.size
        return items.size
    }

    fun listUpdatedAddition() {
        notifyItemInserted(items.size - 1)
    }

    fun listUpdatedUpdate(index: Int) {
        notifyItemChanged(index)
    }
}