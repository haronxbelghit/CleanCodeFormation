package com.sqli.cleancodeformation.presentation.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sqli.cleancodeformation.R
import com.sqli.cleancodeformation.databinding.FragmentAddUserBinding
import com.sqli.cleancodeformation.presentation.viewmodel.AddUserViewModel
import com.sqli.cleancodeformation.presentation.viewmodel.UserSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private val viewModel: AddUserViewModel by viewModels()
    private val sharedViewModel: UserSharedViewModel by activityViewModels()
    private lateinit var profilePicture: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupActionBar()
        setupSaveButton()
        setupSelectImageButton()
    }

    private fun setupActionBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "New User Form"
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black,
                    ),
                ),
            )
        }
    }

    private fun setupSaveButton() {
        binding.saveButton.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val city = binding.etCity.text.toString()
            val country = binding.etCountry.text.toString()
            val job = binding.etJob.text.toString()
            val desc = binding.etDesc.text.toString()
            val phone = binding.etPhone.text.toString()
            val tel = binding.etTel.text.toString()
            val email = binding.etEmail.text.toString()

            if (firstName.isEmpty() || profilePicture.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter a first name and profile picture at least",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            viewModel.addUser(
                firstName,
                profilePicture,
                lastName,
                city,
                country,
                job,
                desc,
                phone,
                tel,
                email,
            )
            sharedViewModel.onUserAdded()
            findNavController().navigateUp()
        }
    }

    private fun setupSelectImageButton() {
        binding.selectImageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                selectImage()
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION,
                )
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permission denied, unable to select image",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            Glide.with(requireContext()).load(imageUri).into(binding.userProfilePicture)
            profilePicture = imageUri.toString()
        }
    }

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1
        private const val REQUEST_CODE_PICK_IMAGE = 2
    }
}
