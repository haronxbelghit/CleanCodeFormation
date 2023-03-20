package com.sqli.cleancodeformation.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sqli.cleancodeformation.databinding.ItemUserBinding
import com.sqli.cleancodeformation.domain.model.User


class UserListAdapter(
    private var userList: MutableLiveData<List<User>>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.root.setOnClickListener { user.id?.let { it1 -> listener.onItemClick(it1) } }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    // Define an interface for the item click listener
    interface OnItemClickListener {
        fun onItemClick(id: Int)
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

@BindingAdapter("profilePictureUri")
fun ImageView.loadProfilePicture(url: String?) {
    if (url != null) {
        Glide.with(context)
            .load(url)
            .into(this)
    }
}
