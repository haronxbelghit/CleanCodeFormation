package com.sqli.cleancodeformation.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.databinding.ItemUserBinding
import com.sqli.cleancodeformation.domain.model.User


class UserListAdapter(private var userList: MutableLiveData<List<User>>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList.value?.get(position)
        user?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int {
        return userList.value?.size ?: 0
    }

    fun setData(userList: List<User>) {
        this.userList.postValue(userList)
    }
}
