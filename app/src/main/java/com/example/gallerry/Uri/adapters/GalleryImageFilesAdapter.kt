package com.example.gallerry.Uri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallerry.R
import com.example.gallerry.uri.Uri.listeners.FileSelectionListener
import java.io.File

class GalleryImageFilesAdapter (private val listener: FileSelectionListener) : RecyclerView.Adapter<GalleryImageFilesAdapter.ImageViewHolder>() {

    private var mFiles = mutableListOf<File>()

    fun setItems(files: List<File>) {
        mFiles = files.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mFiles.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.new(parent, listener)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(mFiles[position])
    }

    class ImageViewHolder(itemView: View, private val listener: FileSelectionListener) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.viewholderImageView

        companion object {
            fun new(viewGroup: ViewGroup, listener: FileSelectionListener) = ImageViewHolder(
                LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.viewholder_image_file, viewGroup, false), listener)
        }

        fun bind(file: File) {
            imageView.loadImage(file)
            imageView.setOnClickListener({ listener.onFileSelected(file) })
        }
    }
}

