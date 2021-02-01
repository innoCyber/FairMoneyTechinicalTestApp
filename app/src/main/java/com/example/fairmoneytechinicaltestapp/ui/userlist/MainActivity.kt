package com.example.fairmoneytechinicaltestapp.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fairmoneytechinicaltestapp.databinding.ActivityMainBinding
import com.example.fairmoneytechinicaltestapp.ui.userdetail.UserDetailActivity
import com.example.fairmoneytechinicaltestapp.ui.userlist.adapter.UserListAdapter
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),UserListAdapter.UserItemListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
        viewModel.getAllUsers()

    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter(this)
        binding.userRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerview.adapter = adapter
    }


    private fun setupObservers() {
        viewModel.fetchAllUsers.observe(this, { uiStatus ->
            when (uiStatus.status) {
                UIStatus.Status.SUCCESS -> {
                    binding.userListProgressBar.visibility = View.GONE
                }
                UIStatus.Status.ERROR ->
                    uiStatus.message?.let {
                        showError(it)
                        binding.userListProgressBar.visibility = View.GONE

                    }

                UIStatus.Status.LOADING ->
                    binding.userListProgressBar.visibility = View.VISIBLE
            }
        })

        viewModel.userList.observe(this, {
            if (it.isNotEmpty())
                adapter.setItems(it)
        })

    }


    override fun onUserClicked(userId: String) {

        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.USER_ID, userId)
        startActivity(intent)
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(binding.userListLayout, errorMessage, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss") {
        }.show()
    }
}