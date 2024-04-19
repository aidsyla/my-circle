package com.cacttuseducation.mycircle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.cacttuseducation.mycircle.databinding.ActivityLoginBinding
import com.cacttuseducation.mycircle.helpers.Helpers
import com.cacttuseducation.mycircle.models.Login
import com.cacttuseducation.mycircle.models.RegisterRequestDto
import com.cacttuseducation.mycircle.models.UserResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            apiCall()
        }

        binding.btnRegister.setOnClickListener {
            registerApiCall()
        }

        toggleLoginOrRegister()
    }

    private fun toggleLoginOrRegister() {
        binding.btnToggleLogin.setOnClickListener {
            binding.layoutBoxUsernameOrEmail.visibility = View.VISIBLE
            binding.layoutBoxPassword.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.VISIBLE

            binding.layoutBoxFirstName.visibility = View.GONE
            binding.layoutBoxLastName.visibility = View.GONE
            binding.layoutBoxEmail.visibility = View.GONE
            binding.layoutBoxUsername.visibility = View.GONE
            binding.layoutBoxPasswordRegister.visibility = View.GONE
            binding.layoutBoxNumberRegister.visibility = View.GONE
            binding.layoutBioRegister.visibility = View.GONE
            binding.layoutBoxPasswordRegister.visibility = View.GONE
            binding.btnRegister.visibility = View.GONE
        }

        binding.btnToggleRegister.setOnClickListener {
            binding.layoutBoxUsernameOrEmail.visibility = View.GONE
            binding.layoutBoxPassword.visibility = View.GONE
            binding.btnLogin.visibility = View.GONE

            binding.layoutBoxFirstName.visibility = View.VISIBLE
            binding.layoutBoxLastName.visibility = View.VISIBLE
            binding.layoutBoxEmail.visibility = View.VISIBLE
            binding.layoutBoxUsername.visibility = View.VISIBLE
            binding.layoutBoxPasswordRegister.visibility = View.VISIBLE
            binding.layoutBoxNumberRegister.visibility = View.VISIBLE
            binding.layoutBioRegister.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.VISIBLE
        }
    }

    private fun registerApiCall() {
        val registerRequest = RegisterRequestDto(
            binding.etBoxFirstName.text.toString(),
            binding.etBoxLastName.text.toString(),
            binding.etRegisterEmail.text.toString(),
            binding.etRegisterUsername.text.toString(),
            binding.etPasswordRegister.text.toString(),
            binding.etNumberRegister.text.toString(),
            binding.etBioRegister.text.toString(),
        )

        Helpers.provideRetrofitInstance().requestRegister(registerRequest).enqueue(object : Callback<UserResponseDto?> {
            override fun onResponse(
                call: Call<UserResponseDto?>,
                response: Response<UserResponseDto?>
            ) {
                if (response.body()?.success == true) {
                    Toast.makeText(applicationContext, "You have registered successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserResponseDto?>, t: Throwable) {
                Toast.makeText(applicationContext, "@$t", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun apiCall() {
        val requestDto = Login(
            binding.etLoginByUsernameOrEmail.text.toString(),
            binding.etLoginByUsernameOrEmail.text.toString(),
            binding.etPassword.text.toString())

        Helpers.provideRetrofitInstance().requestLogin(requestDto).enqueue(object : Callback<UserResponseDto?> {
            override fun onResponse(call: Call<UserResponseDto?>, response: Response<UserResponseDto?>) {
                Log.d("TAG", "onResponse ${response.body()}")
                if (response.body()?.success == true) {
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_LONG).show()

                    useSharedPref("username", response.body()!!.username)
                    useSharedPref("firstName", response.body()!!.firstName)
                    useSharedPref("lastName", response.body()!!.lastName)
                    useSharedPref("email", response.body()!!.email)
                    useSharedPref("bio", response.body()!!.bio)
                    Helpers.putStringOnSharedPrefs(applicationContext, "image", response.body()!!.name)


                    val intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                } else{
                    Toast.makeText(applicationContext, "Wrong credentials!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserResponseDto?>, t: Throwable) {
                Toast.makeText(applicationContext, "Cannot establish connection", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun useSharedPref(key: String, value: String){
        Helpers.putStringOnSharedPrefs(applicationContext, key,
            value)
    }

}



