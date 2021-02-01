package com.example.fairmoneytechinicaltestapp.ui.userdetail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.databinding.ActivityUserDetailBinding
import com.example.fairmoneytechinicaltestapp.util.UIStatus
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(USER_ID)?.let { id ->
            viewModel.getUserDetail(id)
            setupObservers(id)
        } ?: showError("User not found")

    }

    private fun setupObservers(id:String) {

        viewModel.getUserFromRemote(id).observe(this, { uiStatus ->
            when (uiStatus.status) {
                UIStatus.Status.SUCCESS -> {
                    binding.userDetailsProgressBar.visibility = View.GONE
                }
                UIStatus.Status.ERROR ->
                    uiStatus.message?.let {
                        showError(it)
                        binding.userDetailsProgressBar.visibility = View.GONE

                    }

                UIStatus.Status.LOADING ->
                    binding.userDetailsProgressBar.visibility = View.VISIBLE
            }
        })

        viewModel.userFromLocal(id).observe(this, {
            if (it != null)
                bindUser(it)
        })

    }


    @SuppressLint("SetTextI18n")
    private fun bindUser(user: User) {
        binding.nameTextview.text = "${user.title} ${user.firstName} ${user.lastName}"
        binding.emailTextview.text = user.email
        binding.phoneTextview.text = user.phone
        binding.dobTextview.text = user.dateOfBirth
        Glide.with(binding.root)
            .load(user.picture)
            .transform(CircleCrop())
            .into(binding.userDetailImage)
    }

    private fun showError(errorMessage: String) {
        Snackbar.make(binding.userDetailsLayout, errorMessage, Snackbar.LENGTH_INDEFINITE).setAction("Dismiss") {
        }.show()
    }

    companion object {
        const val USER_ID = "user_id"
    }
}