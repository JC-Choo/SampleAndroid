package dev.chu.recyclerviewkotlin.flowerList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.recyclerviewkotlin.R
import dev.chu.recyclerviewkotlin.data.Flower

class FlowersAdapter(private val onClick: (Flower) -> Unit) :
    ListAdapter<Flower, FlowersAdapter.FlowerViewHolder>(FlowerDiffCallback) {

    companion object FlowerDiffCallback : DiffUtil.ItemCallback<Flower>() {
        override fun areItemsTheSame(oldItem: Flower, newItem: Flower): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Flower, newItem: Flower): Boolean {
            return oldItem == newItem
        }
    }

    class FlowerViewHolder(view: View, val onClick: (Flower) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val flowerTextView: TextView = itemView.findViewById(R.id.flower_text)
        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var currentFlower: Flower? = null

        init {
            itemView.setOnClickListener {
                currentFlower?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image. */
        fun bind(flower: Flower) {
            currentFlower = flower

            flowerTextView.text = flower.name
            if (flower.image != null) {
                flowerImageView.setImageResource(flower.image)
            } else {
                flowerImageView.setImageResource(R.drawable.rose)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_flower, parent, false)
        return FlowerViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}