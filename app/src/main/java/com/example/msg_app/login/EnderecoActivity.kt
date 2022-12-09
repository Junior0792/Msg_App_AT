package com.example.msg_app.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.msg_app.R
import com.example.msg_app.models.User
import com.example.msg_app.models.UserResponse

class EnderecoActivity   : AppCompatActivity() {


    lateinit var viewModel: EnderecoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endereco)

        val user_id = intent.getStringExtra("user_id")
        initViewModel()
        createUserObservable()

        if(user_id != null) {
            loadUserData(user_id)
        }
        createButton.setOnClickListener {
            createUser(user_id)
        }
        deleteButton.setOnClickListener {
            deleteUser(user_id)
        }
    }

    private fun deleteUser(user_id: String?) {
        viewModel.getDeleteUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@EnderecoActivity, "Failed to delete user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@EnderecoActivity, "Successfully deleted user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModel.deleteUser(user_id)
    }
    private fun loadUserData(user_id: String?) {
        viewModel.getLoadUserObservable().observe(this, Observer <UserResponse?>{
            if(it != null) {
                editTextName.setText(it.data?.name)
                editTextEmail.setText(it.data?.email)
                createButton.setText("Update")
                deleteButton.visibility =  View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }
    private fun createUser(user_id: String?){
        val user = User("", editTextName.text.toString(), editTextEmail.text.toString(), "Active", "Male")

        if(user_id == null)
            viewModel.createUser(user)
        else
            viewModel.updateUser(user_id, user)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EnderecoViewModel::class.java)

    }

    private fun createUserObservable() {
        viewModel.getEnderecoObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@EnderecoActivity, "Failed to create/update new user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@EnderecoActivity, "Successfully created/updated user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

}





