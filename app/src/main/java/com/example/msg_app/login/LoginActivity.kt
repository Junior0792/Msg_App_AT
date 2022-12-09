package com.example.msg_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.msg_app.main.MainActivity
import com.example.msg_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val viewModel by viewModels<LoginViewModel>()


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()
        if(viewModel.isLoggedIn()){
            startMainActivity()
        }
    }



    fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}
