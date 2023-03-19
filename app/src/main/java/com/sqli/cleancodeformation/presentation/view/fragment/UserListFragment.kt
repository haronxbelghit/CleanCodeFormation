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
        recyclerView = binding.userListRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UserListAdapter(MutableLiveData(emptyList()))
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        viewModel.userList.observe(viewLifecycleOwner) { userList ->
            adapter.setData(userList)
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addUserFAB.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }
        return binding.root
    }
}
