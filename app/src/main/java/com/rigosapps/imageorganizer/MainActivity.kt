package com.rigosapps.imageorganizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.databinding.ActivityMainBinding
import com.rigosapps.imageorganizer.helpers.TimeHelper
import com.rigosapps.imageorganizer.model.Folder
import com.rigosapps.imageorganizer.model.ImageItem
import com.rigosapps.imageorganizer.screens.HomeFragment
import com.rigosapps.imageorganizer.viewModels.ItemViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        Timber.plant(Timber.DebugTree())
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {

            // 1
            val mainFragment = HomeFragment.newInstance()

            // 2
            val fragmentContainerViewId: Int = binding.detailContainer.id
            supportFragmentManager.beginTransaction().replace(fragmentContainerViewId, mainFragment)
                .commitNow()
        }


        binding.customDrawer.addFolderButton.setOnClickListener() {
            showCreateFolderDialog()

        }

        val drawerLayout = binding.myDrawerLayout
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {

                itemViewModel.updateImageItem(data.getParcelableExtra(INTENT_LIST_KEY)!!)

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    public fun showListDetail(id: Long) {

        //using normal layout

        val listDetailIntent = Intent(
            this, ListDetailActivity::class.java
        )
        listDetailIntent.putExtra(INTENT_LIST_KEY, id)
        startActivityForResult(
            listDetailIntent, LIST_DETAIL_REQUEST_CODE
        )
    }


    private fun showCreateFolderDialog() {
        // 1
        val dialogTitle = getString(R.string.name_of_folder)
        val positiveButtonTitle = getString(R.string.create_list)
// 2
        val builder = AlertDialog.Builder(this)
        val folderTitleText = EditText(this)
        folderTitleText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(folderTitleText)
        lateinit var imageItem: ImageItem


// 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val folder = Folder(
                0,
                folderTitleText.text.toString(),
            )


            dialog.dismiss()
        }
// 4
        builder.create().show()


    }


    companion object {
        const val INTENT_LIST_KEY = "imageItem"
        const val LIST_DETAIL_REQUEST_CODE = 10
    }

}



