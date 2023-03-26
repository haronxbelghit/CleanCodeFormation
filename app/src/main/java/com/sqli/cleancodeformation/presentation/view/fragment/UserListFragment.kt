package com.sqli.cleancodeformation.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentUserListBinding
import com.sqli.cleancodeformation.presentation.adapter.UserListPagingAdapter
import com.sqli.cleancodeformation.presentation.viewmodel.UserListViewModel
import com.sqli.cleancodeformation.presentation.viewmodel.UserSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UserListViewModel
    private val sharedViewModel: UserSharedViewModel by activityViewModels()
    private lateinit var adapter: UserListPagingAdapter

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

        adapter = UserListPagingAdapter(object : UserListPagingAdapter.OnItemClickListener() {
            override fun onItemClick(id: Int) {
                viewModel.onUserClicked(id)
            }
        })

        recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.data.collectLatest {
                adapter.submitData(it)
            }
        }

        sharedViewModel.userAdded.asLiveData().observe(viewLifecycleOwner) { userAdded ->
            if (userAdded) {
                adapter.refresh()
            }
        }


        viewModel.selectedUserId.observe(viewLifecycleOwner) { userId ->
            val action =
                UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(userId)
            findNavController().navigate(action)
        }

        binding.addUserFAB.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }
        return binding.root
    }
}