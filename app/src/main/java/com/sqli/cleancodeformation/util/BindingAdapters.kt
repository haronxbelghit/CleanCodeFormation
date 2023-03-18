package com.sqli.cleancodeformation.util

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.presentation.adapter.UserListAdapter

open class BindingAdapters {
    @BindingAdapter("userList")
    fun setUsers(recyclerView: RecyclerView, users: List<User>?) {
        if (users != null && users.isNotEmpty()) {
            recyclerView.adapter = UserListAdapter(users)
        } else {
            recyclerView.adapter = null
            recyclerView.background =
                ContextCompat.getDrawable(recyclerView.context, R.drawable.bg_no_users)
        }
    }

    private fun UserListAdapter(clickListener: List<User>): UserListAdapter {
        return UserListAdapter(object : UserListAdapter.ClickListener {
            override fun onClick(user: User) {
                // handle click event
            }
        })
    }

}