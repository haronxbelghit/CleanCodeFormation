package com.sqli.cleancodeformation.presentation.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentGetUserDetailsBinding
import com.sqli.cleancodeformation.presentation.viewmodel.GetUserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GetUserDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGetUserDetailsBinding
    private lateinit var viewModel: GetUserDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GetUserDetailsViewModel::class.java]
        val userId = arguments?.getInt("userId") ?: 0
        viewModel.getUserById(userId)

        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            // Update the UI with the user details
            binding.usernameById.text = user.username
            // Set the user's profile image using Glide
            val uri = Uri.parse(user.profilePictureUri)

            Glide.with(binding.profilePicById.context)
                .load(uri)
                .apply(RequestOptions().error(R.drawable.bg_no_users))
                .into(binding.profilePicById)
        }
    }
}



