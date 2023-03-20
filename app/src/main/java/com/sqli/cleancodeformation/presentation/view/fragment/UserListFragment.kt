package com.sqli.cleancodeformation.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentUserListBinding
import com.sqli.cleancodeformation.presentation.adapter.UserListAdapter
import com.sqli.cleancodeformation.presentation.viewmodel.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: UserListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentUserListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        recyclerView = binding.userListRecyclerView
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = UserListAdapter(MutableLiveData(emptyList()),
            object : UserListAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
                    viewModel.onUserClicked(id)
                }
            })

        viewModel.selectedUserId.observe(viewLifecycleOwner) { userId ->
            val action = UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(userId)
            findNavController().navigate(action)
        }

        recyclerView.adapter = adapter
        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            adapter.setData(userList)
        }
        binding.addUserFAB.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }
        return binding.root
    }
}