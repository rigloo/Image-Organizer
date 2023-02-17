package com.rigosapps.imageorganizer.screens

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.ListDetailActivity
import com.rigosapps.imageorganizer.MainActivity
import com.rigosapps.imageorganizer.databinding.ListDetailFragmentBinding
import com.rigosapps.imageorganizer.helpers.FileHelper
import com.rigosapps.imageorganizer.viewModels.ImageItem
import com.rigosapps.imageorganizer.viewModels.ListDetailViewModel
import timber.log.Timber


class ListDetailFragment : Fragment() {
    private var imageUri: Uri? = null
    private lateinit var viewModel: ListDetailViewModel
    private lateinit var binding: ListDetailFragmentBinding

    companion object {

        fun newInstance() = ListDetailFragment()
        val IMAGE_CAPTURE_CODE = 1000
        val PICK_IMAGE = 1230

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)

        val imageItem: ImageItem? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)
        if (imageItem != null) {
            viewModel.imageItem = imageItem
            requireActivity().title = imageItem.title
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = ListDetailFragmentBinding.inflate(inflater, container, false)

        binding.saveButton.setOnClickListener() {

            viewModel.imageItem.description = binding.editTextDescription.text.toString()

            if (imageUri == null) {

            } else {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri!!)
                val path =
                    FileHelper.saveToInternalStorage(bitmap, (activity as ListDetailActivity?)!!)
                viewModel.imageItem.imagePath = path
            }
            showAlert()
        }

        binding.editTextDescription.setText(viewModel.imageItem.description)

        if (viewModel.imageItem.imagePath == "null") {
            binding.imageViewDetail.visibility = View.GONE
            binding.imageGallery.imageCamera.visibility = View.VISIBLE

        } else {
            val imageBitmap = FileHelper.loadImageFromStorage(viewModel.imageItem.imagePath!!)
            binding.imageViewDetail.setImageBitmap(imageBitmap)
        }



        binding.imageGallery.cameraButton.setOnClickListener() {
            val context = (activity as ListDetailActivity?)!!

            if (context.permissionGranted) {
                openCameraInterface()
            } else {
                context.showAlert("Could not get required permissions to do this operation.")
//            }
            }
        }

        binding.imageGallery.galleryButton.setOnClickListener() {
            openGallery()
        }


        return binding.root
    }

    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Take Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Description")
        imageUri = activity?.contentResolver?.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
        )// Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)// Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE_CODE)
    }

    private fun openGallery() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {


            binding.imageViewDetail.setImageURI(imageUri)
            binding.imageViewDetail.visibility = View.VISIBLE
            binding.imageGallery.imageCamera.visibility = View.GONE
            Timber.e("Received image input ${view}")
        }
    }

    private fun showAlert() {

        Toast.makeText(
            requireActivity(), "Entry saved", Toast.LENGTH_SHORT
        ).show()

    }

}