package com.rigosapps.imageorganizer.screens

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rigosapps.imageorganizer.MainActivity
import com.rigosapps.imageorganizer.R
import com.rigosapps.imageorganizer.adapters.ItemAdapter
import com.rigosapps.imageorganizer.databinding.FragmentHomeBinding
import com.rigosapps.imageorganizer.helpers.TimeHelper
import com.rigosapps.imageorganizer.model.ImageItem
import com.rigosapps.imageorganizer.viewModels.ItemViewModel
import timber.log.Timber


/*
The first page that comes up when loading the app. Will load all the image entries along with the title and date
information and displays it using a RecyclerView list
 */

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var itemViewModel: ItemViewModel


    companion object {

        fun newInstance() = HomeFragment()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    //set the binding, get the ItemViewModel (same one from MainActivity)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)


        itemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)

//        val args = arguments
//        val index = args!!.getLong(MainActivity.FOLDER_ID, -)


        val itemAdapter = ItemAdapter(::onItemClick)
        binding.homeList.adapter = itemAdapter
        binding.homeList.layoutManager = LinearLayoutManager(requireContext())
        itemViewModel.readData.observe(viewLifecycleOwner) { imageItems ->
            itemAdapter.setData(imageItems)

        }


        //Set a listener whenever you click the Add button on the bottom right.
        binding.floatingActionButton.setOnClickListener() {

            showCreateListDialog()

        }


        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    Timber.e("Getting ItemId ${viewHolder.adapterPosition}")

                    itemViewModel.deleteImageItem(itemAdapter.getItembyPosition(viewHolder.adapterPosition))
                    Toast.makeText(
                        requireActivity(),
                        "Deleted",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.homeList)

        return binding.root
    }

    fun onItemClick(id: Long) {

        (activity as MainActivity?)!!.showListDetail(id)
    }

    //Show a dialog to create a image entry, then navigate to the DetailActivity
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
                0,
                "null",
                listTitleEditText.text.toString(),
                TimeHelper.getStringfromDate(TimeHelper.getCurrentTime()),
                "",
                itemViewModel.currentFolderId
            )

            val id = itemViewModel.addItem(
                imageItem
            )
            dialog.dismiss()
            (activity as MainActivity?)!!.showListDetail(id)
        }
// 4
        builder.create().show()


    }


}
