package com.value.fakestore.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.domain.common.model.Product
import com.value.fakestore.databinding.ItemProductGridBinding
import com.value.fakestore.databinding.ItemProductHorizontalBinding


class ProductsAdapter(
    val onItemClickListener: (Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<Product> = ArrayList()
        set(value) {
            val oldList = ArrayList(field)
            field.clear()
            field.addAll(value)
            val newList = ArrayList(field)
            val diffCallback = ProductDiffCallback(oldList, newList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    var listViewType: Int = RecyclerView.VERTICAL
        set(value) {
            field = value
            val diffCallback = ProductDiffCallback(items, items)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemViewType(position: Int): Int = listViewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            RecyclerView.HORIZONTAL -> {
                val binding = ItemProductHorizontalBinding.inflate(inflater, parent, false)
                HorizontalViewHolder(binding)
            }
            else -> {
                val binding = ItemProductGridBinding.inflate(inflater, parent, false)
                GridViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when (holder) {
            is HorizontalViewHolder -> {
                holder.binding.item = item
                holder.binding.card.setOnClickListener {
                    onItemClickListener(item)
                }
            }
            is GridViewHolder -> {
                holder.binding.item = item
                holder.binding.card.setOnClickListener {
                    onItemClickListener(item)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    inner class HorizontalViewHolder(val binding: ItemProductHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class GridViewHolder(val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class ProductDiffCallback(
        private val oldList: List<Product>,
        private val newList: List<Product>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            return oldList[oldPosition] === newList[newPosition]
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }
}