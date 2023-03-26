package com.sqli.cleancodeformation.presentation.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentGetUserDetailsBinding
import com.sqli.cleancodeformation.presentation.viewmodel.GetUserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class GetUserDetailsFragment : Fragment() {
    private lateinit var binding: FragmentGetUserDetailsBinding
    private lateinit var viewModel: GetUserDetailsViewModel
    private val PERMISSION_CALL = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGetUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded", "SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[GetUserDetailsViewModel::class.java]
        val userId = arguments?.getInt("userId") ?: 0
        viewModel.getUserById(userId)

        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            // Update the UI with the user details
            binding.usernameById.text = user.firstName + " " + user.lastName
            binding.tvResCity.text = user.city
            binding.tvResCountry.text = user.country
            binding.tvResJob.text = user.job
            binding.tvResDesc.text = user.desc
            binding.tvResTel.text = user.tel
            binding.tvResPhone.text = user.phone
            binding.tvResEmail.text = user.email
            // Set the user's profile image using Glide
            val uri = Uri.parse(user.profilePictureUri)

            Glide.with(binding.profilePicById.context)
                .load(uri)
                .apply(RequestOptions().error(R.drawable.bg_no_users))
                .into(binding.profilePicById)
        }
        binding.tvResPhone.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.CALL_PHONE), 0
                )
            } else {
                makeCall(binding.tvResPhone.text.toString())
            }
        }
        binding.tvResEmail.setOnClickListener {
            val resolveIntent = Intent(Intent.ACTION_SENDTO)
            resolveIntent.data = Uri.parse("mailto:default@recipient.com")
            val resolveInfoList =
                context?.packageManager?.queryIntentActivities(
                    resolveIntent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            val intents = resolveInfoList?.mapNotNull { info ->
                context?.packageManager?.getLaunchIntentForPackage(info.activityInfo.packageName)
            }
                ?.toMutableList()
            if (intents?.isEmpty() == true) {
                //no mail client installed. Prompt user or throw exception
            } else if (intents != null) {
                val chooser = Intent(Intent.ACTION_CHOOSER)
                chooser.putExtra(Intent.EXTRA_INTENT, intents.removeAt(0))
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents.toTypedArray())
                chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(chooser)
//                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CALL -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall(binding.tvResPhone.text.toString())
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_CALL
                )
            }
        }
    }

    private fun makeCall(num: String) {
        val callIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${num}"))
        startActivity(callIntent)
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title =
                viewModel.selectedUser.value?.firstName + " " + viewModel.selectedUser.value?.lastName
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            )
        }
    }
}



