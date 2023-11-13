package com.example.avitorecyclerview121123

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.avitorecyclerview121123.databinding.NumberItemBinding

interface NumberDelegate {
    fun removeElement(number: Int)
}

class RecyclerViewAdapter :
    ListAdapter<Int, RecyclerViewAdapter.ViewHolder>(diffUtil) {

    private var delegate: NumberDelegate? = null

    fun attachDelegate(delegate: NumberDelegate) {
        this.delegate = delegate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NumberItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), delegate
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.numberModel = getItem(position)
    }


    class ViewHolder(
        private var binding: NumberItemBinding,
        private var delegate: NumberDelegate?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var numberModel: Int? = null
            set(value) {
                value?.let { newValue ->
                    field = newValue
                    binding.number.text = newValue.toString()
                    binding.button.setOnClickListener {
                        delegate?.removeElement(numberModel!!)
                    }
                }
            }

    }

    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<Int>() {
            override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
                return oldItem == newItem
            }
        }
    }


}