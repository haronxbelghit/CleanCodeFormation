package com.sqli.cleancodeformation.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentUserListBinding
import com.sqli.cleancodeformation.domain.model.User
import com.sqli.cleancodeformation.presentation.adapter.UserListAdapter
import com.sqli.cleancodeformation.presentation.viewmodel.UserListViewModel
import com.sqli.cleancodeformation.util.BindingAdapters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding

    // Declare dataBindingComponent
    private val dataBindingComponent = object : DataBindingComponent {
        override fun getBindingAdapters(): BindingAdapters {
            return BindingAdapters()
        }
    }

    private val viewModel: UserListViewModel by viewModels()
    private val adapter = UserListAdapter(object : UserListAdapter.ClickListener {
        override fun onClick(user: User) {
            // handle click event
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //binding = FragmentUserListBinding.inflate(inflater, container, false)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_list,
            container,
            false,
            dataBindingComponent
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userRecyclerView.adapter = adapter
        binding.viewModel = viewModel

        //setupRecyclerView()
        binding.addUserFAB.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            if (userList != null) adapter.setUsers(userList)
        }

        viewModel.getUsers()
    }

}
