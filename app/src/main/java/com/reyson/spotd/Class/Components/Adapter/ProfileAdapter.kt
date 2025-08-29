package com.reyson.spotd.Class.Components.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reyson.spotd.Class.Model.Image
import com.reyson.spotd.R
import com.squareup.picasso.Picasso

class ProfileAdapter(
    private var imageInfoList: ArrayList<Image>,
    private val imageListener: ImageListener?
) : RecyclerView.Adapter<ProfileAdapter.ImageViewHolder>() {
    private val itemDataList = ArrayList<ItemData>()
    private val selectedPositions = ArrayList<Int>() // To track selected positions
    private val selectedImages = ArrayList<Image>() // Track selected images

    init {
        for (i in imageInfoList.indices) {
            itemDataList.add(ItemData())
        }
    }

    fun setImageList(imageList: ArrayList<Image>) {
        imageInfoList = imageList
        itemDataList.clear()
        selectedPositions.clear() // Clear selected positions
        for (i in imageList.indices) {
            itemDataList.add(ItemData())
        }
        notifyDataSetChanged() // Notify the adapter that the data has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_select_image_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = imageInfoList[position]
        Picasso.get().load("file://" + imageUri.path).placeholder(R.drawable.img_logo)
            .into(holder.imageView)
        val itemData = itemDataList[position]
        val isSelected = selectedImages.contains(imageUri)
        holder.test11.visibility = if (isSelected) View.VISIBLE else View.GONE
        // holder.test11.setVisibility(itemData.isTestVisible() ? View.VISIBLE : View.GONE);
        holder.img_check.visibility = if (isSelected) View.VISIBLE else View.GONE
        holder.imageView.setOnClickListener { view: View? ->
            if (selectedImages.size < 1 || isSelected) {
                if (isSelected) {
                    // Deselect the item
                    selectedImages.remove(imageUri)
                } else {
                    // Select the item
                    selectedImages.add(imageUri)
                }
                notifyDataSetChanged()
                imageListener?.onImageSelected(selectedImages)
            } else {
                // Already selected 2 items
                // Handle accordingly
            }
        }
    }

    override fun getItemCount(): Int {
        return imageInfoList.size
    }

    interface ImageListener {
        fun onImageSelected(selectedImages: ArrayList<Image>)
    }

    fun getSelectedPositions(): ArrayList<Int> {
        val selectedPositions = ArrayList<Int>()
        for (i in imageInfoList.indices) {
            if (imageInfoList[i].isSelected) {
                selectedPositions.add(i)
            }
        }
        return selectedPositions
    }

    fun getSelectedImages(): ArrayList<Image> {
        val selectedPositions = getSelectedPositions()
        val selectedImages = ArrayList<Image>()
        for (position in selectedPositions) {
            selectedImages.add(imageInfoList[position])
        }
        return selectedImages
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var img_check: ImageView
        var test11: LinearLayout
        var textView: TextView? = null

        init {
            imageView = itemView.findViewById(R.id.scsii_photos)
            test11 = itemView.findViewById(R.id.scsii_select_hover)
            img_check = itemView.findViewById(R.id.scsii_image_check)
        }
    }

    private class ItemData {
        var isTestVisible = false
    }
}