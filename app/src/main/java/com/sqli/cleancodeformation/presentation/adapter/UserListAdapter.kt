package com.sqli.cleancodeformation.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.ItemUserBinding
import com.sqli.cleancodeformation.domain.model.User


class UserListAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private var userList = emptyList<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<User>) {
        this.userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user, clickListener)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding: ItemUserBinding = ItemUserBinding.bind(itemView)

        fun bind(user: User, clickListener: ClickListener) {
            binding.user = user
            binding.root.setOnClickListener {
                clickListener.onClick(user)
            }
        }
    }

    interface ClickListener {
        fun onClick(user: User)
    }
}

