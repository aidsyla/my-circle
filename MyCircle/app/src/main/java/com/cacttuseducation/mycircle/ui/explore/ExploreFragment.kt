package com.cacttuseducation.mycircle.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.cacttuseducation.mycircle.adapters.ProfilePageAdapter
import com.cacttuseducation.mycircle.databinding.FragmentExploreBinding
import com.cacttuseducation.mycircle.viewmodels.GlobalViewModel

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: GlobalViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GlobalViewModel::class.java]
        viewModel.getPosts()
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            val adapter = viewModel.posts.value?.let { it1 -> ProfilePageAdapter(it1.listOfPosts) }
            binding.rvExplore.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rvExplore.adapter = adapter
            binding.progressBarExplore.visibility = View.GONE
        })
    }
}