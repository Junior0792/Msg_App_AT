package com.example.msg_app.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.msg_app.main.MainActivity
import com.example.msg_app.R
import com.example.msg_app.databinding.FragmentSignOnBinding
import com.example.msg_app.utils.getTextInput
import com.example.msg_app.utils.nav
import com.example.msg_app.utils.toast


class SignOnFragment : Fragment() {

    val viewModel by activityViewModels<LoginViewModel> ()

    // Usar a vinculação de visualizações em fragmentos
    // https://developer.android.com/topic/libraries/view-binding?hl=pt-br#fragments

    private var _binding: FragmentSignOnBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignOnBinding.inflate(inflater, container, false)
        val view = binding.root

        setup()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup(){
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.apply {
            btnNext.setOnClickListener {

                onNextClick()


            }
        }
    }


    private fun onNextClick() {

        binding.apply {
            val email = getTextInput(inputEmail)
            val password = getTextInput(inputPassword)
            val confirmPassword = getTextInput(inputConfirmPassword)

            if ( (password != confirmPassword)) {
                toast("Senhas não conferem")
            }
            else if(password.length < 6) {
                toast("Devem ter pelo menos 6 caracteres na senha")
            }
            else {
                saveInputs(email,password)
                next()
            }
        }

    }

    fun signOn(email: String, password: String){
        viewModel.signOn(email, password)
            .addOnSuccessListener {
                toast("Cadastrado com Sucesso")
                startMainActivity()
            }
            .addOnFailureListener {
                toast("Falha ao cadastrar\n${it.message}")
            }
    }

    fun startMainActivity(){
        startActivity(Intent(requireContext(), MainActivity::class.java))
        activity?.finish()
    }

    fun saveInputs(email: String, password: String){
        viewModel.apply {
            setEmail(email)
            setPassword(password)
        }
    }

    fun next(){
        nav(R.id.action_signOnFragment_to_enderecoFragment)
    }

}

