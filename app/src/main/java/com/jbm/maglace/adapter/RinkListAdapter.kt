package com.jbm.maglace.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jbm.maglace.model.Rink
import com.jbm.maglace.databinding.ListItemRinkBinding
import com.jbm.maglace.ui.RinkListFragmentDirections

class RinkListAdapter: ListAdapter<Rink, RinkListAdapter.RinkListViewHolder>(RinkDiffCallback()) {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RinkListViewHolder {
        val rinkItemBinding = ListItemRinkBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)

        return RinkListViewHolder(rinkItemBinding)
    }

    override fun onBindViewHolder(holder: RinkListViewHolder, position: Int) {
        val rink = getItem(position)

        holder.bind(rink)
    }

    class RinkListViewHolder (val rinkItemBinding: ListItemRinkBinding):
        ViewHolder(rinkItemBinding.root) {
        val TAG: String =  "tag.jbm." + this::class.java.simpleName

        init {
            rinkItemBinding.setClickListener { view ->
                rinkItemBinding.rink?.let {
                    navToRinkDetail(view, it.id)
                }
            }
        }

        fun navToRinkDetail(view: View, rinkId: Int) {
            val direction = RinkListFragmentDirections.actionRinklistToRinkdetail(rinkId)
            view.findNavController().navigate(direction)
        }

        fun bind(rink: Rink) {
            rinkItemBinding.rink = rink
            rinkItemBinding.executePendingBindings()
        }
    }
}

private class RinkDiffCallback : DiffUtil.ItemCallback<Rink>() {

    override fun areItemsTheSame(oldItem: Rink, newItem: Rink): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Rink, newItem: Rink): Boolean {
        return oldItem == newItem
    }
}