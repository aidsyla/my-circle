package com.cacttuseducation.mycircle.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacttuseducation.mycircle.adapters.HomePageAdapter
import com.cacttuseducation.mycircle.databinding.FragmentHomeBinding
import com.cacttuseducation.mycircle.viewmodels.GlobalViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: GlobalViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GlobalViewModel::class.java]
        viewModel.getPosts()
        viewModel.posts.observe(viewLifecycleOwner, Observer {
            val adapter = viewModel.posts.value?.let { it1 -> HomePageAdapter(it1.listOfPosts) }
            binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
            binding.rvHome.adapter = adapter
            binding.progressBarHome.visibility = View.GONE
        })
    }
}