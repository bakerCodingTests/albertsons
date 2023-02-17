package com.example.albertsonscoding.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.albertsonscoding.databinding.CardLongformBinding

class LongformAdapter : ListAdapter<String, LongformAdapter.LfViewHolder>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String) =
            oldItem.hashCode() == newItem.hashCode()
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LfViewHolder(parent)

    override fun onBindViewHolder(holder: LfViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class LfViewHolder(
        private val binding: CardLongformBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lf: String) = with(binding) {
            longform = lf
        }

        companion object {
            operator fun invoke(parent: ViewGroup) = CardLongformBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { LfViewHolder(it) }
        }
    }
}
