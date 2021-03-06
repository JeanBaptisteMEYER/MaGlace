package com.jbm.maglace.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.jbm.maglace.R
import com.jbm.maglace.adapter.RinkListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RinkListFragment : Fragment() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    private val mainViewModel: MainViewModel by activityViewModels()

    val rinkListAdapter = RinkListAdapter()

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_rink_list, container, false)

        root.findViewById<RecyclerView>(R.id.rink_list_recyclerview).adapter = rinkListAdapter

        return root
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.liveRinkList.observe(viewLifecycleOwner, {
            Log.d(TAG, "RinkList changed")

            rinkListAdapter.submitList(it)
        })
    }
}