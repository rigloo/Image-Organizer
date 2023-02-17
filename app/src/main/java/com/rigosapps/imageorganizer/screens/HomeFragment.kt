package com.rigosapps.imageorganizer.screens

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.ItemAdapter
import com.rigosapps.imageorganizer.MainActivity
import com.rigosapps.imageorganizer.R
import com.rigosapps.imageorganizer.databinding.FragmentHomeBinding
import com.rigosapps.imageorganizer.helpers.TimeHelper
import com.rigosapps.imageorganizer.viewModels.ImageItem
import com.rigosapps.imageorganizer.viewModels.ItemViewModel


/*
The first page that comes up when loading the app. Will load all the image entries along with the title and date
information and displays it using a RecyclerView list
 */

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: ItemViewModel



    companion object {

        fun newInstance() = HomeFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)


        val adapter = ItemAdapter(viewModel._itemList, ::onItemClick)
        binding.homeList.adapter = adapter


        viewModel.onListAdded = {
            adapter.listUpdatedAddition()
        }

        viewModel.onListUpdated = {
            adapter.listUpdatedUpdate(it)
        }




        binding.floatingActionButton.setOnClickListener() {

            showCreateListDialog()

        }

        return binding.root
    }

    fun onItemClick(imageItem: ImageItem) {

        (activity as MainActivity?)!!.showListDetail(imageItem)
    }

    private fun showCreateListDialog() {
        // 1
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
// 2
        val builder = AlertDialog.Builder(requireActivity())
        val listTitleEditText = EditText(activity)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        lateinit var imageItem: ImageItem


// 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            imageItem = ImageItem(
                java.util.UUID.randomUUID().toString(),
                "null",
                listTitleEditText.text.toString(),
                TimeHelper.getCurrentTime(),
                ""
            )

            viewModel.addItem(
                imageItem
            )
            dialog.dismiss()
            (activity as MainActivity?)!!.showListDetail(imageItem)
        }
// 4
        builder.create().show()


    }


}
