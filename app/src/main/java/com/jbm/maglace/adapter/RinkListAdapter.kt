package com.jbm.maglace.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.jbm.maglace.model.Rink
import com.jbm.maglace.databinding.ListItemRinkBinding

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


/*
class CatalogAdapter (): Adapter <CatalogAdapter.HomeViewHolder>() {

    override fun getItemCount(): Int {
        return catalog.products.size
    }

    class HomeViewHolder(val catalogItemBinding: ListItemCatalogBinding):
            RecyclerView.ViewHolder(catalogItemBinding.root) {
        init {
            catalogItemBinding.setClickListener { view ->
                catalogItemBinding.product?.let {
                    navToDetail(view, it.id)
                }
            }
        }

        fun navToDetail(view: View, productId: Int) {
            val direction = HomeFragmentDirections.actionShowDetailfragment(productId)
            view.findNavController().navigate(direction)
        }

        fun bind(product: Product) {
            catalogItemBinding.product = product
            catalogItemBinding.executePendingBindings()
        }
    }
}
 */