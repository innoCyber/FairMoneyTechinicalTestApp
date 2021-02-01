package com.example.fairmoneytechinicaltestapp.ui.userlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.databinding.UserItemBinding


class UserListAdapter(private val listener: UserItemListener): RecyclerView.Adapter<MainActivityViewHolder>()  {

    interface UserItemListener {
        fun onUserClicked(userId: String)
    }

    private val items = ArrayList<User>()

    fun setItems(items: List<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val binding: UserItemBinding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainActivityViewHolder(binding, listener)    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

}

class MainActivityViewHolder(private val itemBinding: UserItemBinding, private val listener: UserListAdapter.UserItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener{

    private lateinit var user: User

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(user: User) {
        this.user = user
        itemBinding.name.text = "${user.firstName} ${user.lastName}"
        itemBinding.email.text = "${user.email}"
        Glide.with(itemBinding.root)
            .load(user.picture)
            .transform(CircleCrop())
            .into(itemBinding.userImageview)
    }

    override fun onClick(v: View?) {
        listener.onUserClicked(user.id)
    }

}
