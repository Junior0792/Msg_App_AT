package com.example.msg_app.main

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.msg_app.adapter.RecyclerViewAdapter
import com.example.msg_app.databinding.ActivityMainBinding
import com.example.msg_app.login.EnderecoActivity
import com.example.msg_app.login.LoginActivity
import com.example.msg_app.models.User
import com.example.msg_app.models.UserList

class MainActivity : AppCompatActivity(),  RecyclerViewAdapter.OnItemClickListener {

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainViewModel
    val viewModel by viewModels<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setup()
        initRecyclerView()
        initViewModel()
        searchUser()

        createUserButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, EnderecoActivity:class.java))
        }

    }

    private fun searchUser() {
        search_button.setOnClickListener {
            if(!TextUtils.isEmpty(searchEditText.text.toString())) {
                viewModel.searchUser(searchEditText.text.toString())
            } else {
                viewModel.getUsersList()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getUserListObserverable().observe(this, Observer<UserList> {
            if(it == null) {
                Toast.makeText(this@MainActivity, "no result found...", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.userList = it.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getUsersList()
    }

    override fun onItemEditCLick(user: User) {
        val intent = Intent(this@MainActivity, EnderecoActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 1000) {
            viewModel.getUsersList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private fun setup() {
        setupViews()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSair.setOnClickListener {
            viewModel.logout()
            startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupViews() {
        setTitle("Principal")
        binding.tvBemVindo.text = "Seja bem vindo ${viewModel.getCurrentUserEmail()}!"
    }


}