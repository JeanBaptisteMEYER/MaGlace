package com.jbm.maglace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jbm.maglace.adapter.RinkListAdapter
import com.jbm.maglace.data.Rink
import com.jbm.maglace.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RinkListFragment : Fragment() {
    val TAG: String =  "tag.jbm." + this::class.java.simpleName

    private val mainViewModel: MainViewModel by activityViewModels()

    val rinkListAdapter = RinkListAdapter()

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

        mainViewModel.liveRinkList.observe(viewLifecycleOwner, Observer {

            var rinkList = mutableListOf<Rink>()

            for (district in it) {
                for (rink in district.patinoires) {
                    rinkList.add(rink)
                }
            }

            rinkListAdapter.submitList(rinkList)
        })
    }
}

//// create and bind view to the catalog
//        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context),
//            container, false)