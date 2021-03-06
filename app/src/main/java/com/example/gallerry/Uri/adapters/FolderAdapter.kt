package com.example.gallerry.Uri.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gallerry.R
import com.example.gallerry.uri.Uri.listeners.FileSelectionListener
import java.io.File

class FolderAdapter(private val listener: FileSelectionListener) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    private var items = mapOf<File, List<File>>()
    private var indexes = listOf<File>()

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) = holder.bind(getItemAt(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder =
        FolderViewHolder.new(parent, listener)

    override fun getItemCount(): Int = indexes.size

    fun setItems(fileMap: Map<File, List<File>>) {
        items = fileMap
        indexes = fileMap.keys.toList()
        notifyDataSetChanged()
    }

    private fun getItemAt(position: Int) = items[indexes[position]]

    class FolderViewHolder(itemView: View, private val listener: FileSelectionListener) :
        RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.folderImageView
        private val titleTextView = itemView.folderTitleTextView

        companion object {
            fun new(viewGroup: ViewGroup, listener: FileSelectionListener) = FolderViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.viewholder_folder, viewGroup, false), listener
            )
        }

        fun bind(files: List<File>?) {
            (files?.isNotEmpty()).let {
                val firstChild = files!![0]
                val parent = File(firstChild.parent)
                titleTextView.text = parent.nameWithoutExtension
                imageView.loadImage(firstChild)
                itemView.setOnClickListener({listener.onFileSelected(parent) })
            }
        }
    }
}

