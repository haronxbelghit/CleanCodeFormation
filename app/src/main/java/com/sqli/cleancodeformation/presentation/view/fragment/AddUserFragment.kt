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

    private lateinit var profilePicture: String
    private lateinit var firstName: String
    private lateinit var lastName: String
    private lateinit var profilePictureUri: String
    private lateinit var city: String
    private lateinit var country: String
    private lateinit var job: String
    private lateinit var desc: String
    private lateinit var phone: String
    private lateinit var tel: String
    private lateinit var email: String
    private val viewModel: AddUserViewModel by viewModels()
    private lateinit var binding: FragmentAddUserBinding
    private val sharedViewModel: UserSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            firstName = binding.etFirstName.text.toString()
            lastName = binding.etLastName.text.toString()
            city = binding.etCity.text.toString()
            country = binding.etCountry.text.toString()
            job = binding.etJob.text.toString()
            desc = binding.etDesc.text.toString()
            phone = binding.etPhone.text.toString()
            tel = binding.etTel.text.toString()
            email = binding.etEmail.text.toString()

            if (firstName.isEmpty() || profilePicture.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter a first name and profile picture at least",
                    Toast.LENGTH_SHORT
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
                email
            )
            sharedViewModel.onUserAdded()

            findNavController().navigateUp()
        }
        binding.selectImageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission already granted, go ahead and select image
                selectImage()
            } else {
                // Permission not granted, request permission from user
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION || requestCode == REQUEST_CODE_GOOGLE_PHOTOS_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, go ahead and select image
                selectImage()
            } else {
                // Permission denied, show error message or take other appropriate action
                Toast.makeText(
                    requireContext(),
                    "Permission denied, unable to select image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get selected image URI and load it into ImageView using Glide
            val imageUri = data.data
            Glide.with(requireContext())
                .load(imageUri)
                .into(binding.userProfilePicture)
            profilePicture = imageUri.toString()
        }
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "New User Form"
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

    companion object {
        private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 1
        private const val REQUEST_CODE_PICK_IMAGE = 2
        private const val REQUEST_CODE_GOOGLE_PHOTOS_PERMISSIONS = 123

    }
}
