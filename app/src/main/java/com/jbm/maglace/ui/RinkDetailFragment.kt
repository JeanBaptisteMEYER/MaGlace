package com.jbm.maglace.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.jbm.maglace.databinding.FragmentRinkDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RinkDetailFragment : Fragment() {

    private val args: RinkDetailFragmentArgs by navArgs()

    val mainViewModel: MainViewModel by activityViewModels()

    lateinit var rinkBinding: FragmentRinkDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rinkBinding = FragmentRinkDetailBinding.inflate(LayoutInflater.from(context), container, false)

        mainViewModel.setLiveRinkById(args.rinkId)
        mainViewModel.liveRink.observe(viewLifecycleOwner, {
            rinkBinding.rink = it
            rinkBinding.invalidateAll()
        })


        // Inflate the layout for this fragment
        return rinkBinding.root
    }
}