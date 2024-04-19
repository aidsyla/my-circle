package com.cacttuseducation.mycircle.ui.post

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cacttuseducation.mycircle.databinding.FragmentPostBinding
import com.cacttuseducation.mycircle.helpers.Helpers
import com.cacttuseducation.mycircle.helpers.Helpers.provideRetrofitInstance
import com.cacttuseducation.mycircle.models.PostsResponseDto
import com.cacttuseducation.mycircle.models.ToPass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private lateinit var viewModel: PostViewModel
    private var bitmap: Bitmap? = null

    companion object {
        private const val PICK_IMAGE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPost.setOnClickListener {
            apiCall()
        }
    }

    private fun apiCall() {
        Helpers.getStringFromSharedPrefs(requireContext(), "username")
            ?.let {
                ToPass(
                    it,
                    binding.etUserInput.text.toString())
            }?.let {
                provideRetrofitInstance().newPost(it).enqueue(object : Callback<PostsResponseDto?> {
                override fun onResponse(
                    call: Call<PostsResponseDto?>,
                    response: Response<PostsResponseDto?>
                ) {
                    Log.d("TAG","onResponse $response, ${response.body()}")
                    Toast.makeText(requireContext(), "Post successful", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<PostsResponseDto?>, t: Throwable) {
                    Log.d("TAG","onFailure, ${t.message}")
                    Toast.makeText(requireContext(), "Post failed", Toast.LENGTH_LONG).show()
                }
            })
            }
    }

}