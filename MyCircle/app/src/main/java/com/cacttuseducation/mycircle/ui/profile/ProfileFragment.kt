package com.cacttuseducation.mycircle.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.cacttuseducation.mycircle.adapters.ProfilePageAdapter
import com.cacttuseducation.mycircle.databinding.FragmentProfileBinding
import com.cacttuseducation.mycircle.helpers.Helpers
import com.cacttuseducation.mycircle.models.Post
import com.cacttuseducation.mycircle.models.Posts
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvUsername.text = Helpers.getStringFromSharedPrefs(requireContext(), "username")
        binding.tvFirstName.text = Helpers.getStringFromSharedPrefs(requireContext(), "firstName")
        binding.tvLastName.text = Helpers.getStringFromSharedPrefs(requireContext(), "lastName")
        binding.tvEmail.text = Helpers.getStringFromSharedPrefs(requireContext(), "email")
        binding.tvBio.text = Helpers.getStringFromSharedPrefs(requireContext(), "bio")

        val path = Helpers.getStringFromSharedPrefs(requireContext(), "image")
        Picasso.get().load("http://192.168.0.117:8080/api/post/picture/$path").into(binding.imgProfile)

        apiCall()
    }

    private fun apiCall() {

        Helpers.getStringFromSharedPrefs(requireContext(), "username")
            ?.let { Helpers.provideRetrofitInstance().getPostsByUsername(it) }
            ?.enqueue(object : Callback<Posts?> {
                override fun onResponse(call: Call<Posts?>, response: Response<Posts?>) {
                    Log.d("TAG","onResponse: ${response.body()} $response")
                    val listOfPostsByUsername = response.body()?.listOfPosts
                    val adapter = listOfPostsByUsername?.let { ProfilePageAdapter(it) }
                    binding.rvProfile.layoutManager = GridLayoutManager(requireContext(), 3)
                    binding.rvProfile.adapter = adapter
                }

                override fun onFailure(call: Call<Posts?>, t: Throwable) {
                    Log.d("TAG","onFailure: ${t.message}")
                }
            })

    }


}