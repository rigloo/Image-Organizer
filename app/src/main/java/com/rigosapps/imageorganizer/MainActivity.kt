package com.rigosapps.imageorganizer


import android.app.Activity
import android.content.Intent
import android.icu.util.UniversalTimeScale.toLong
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rigosapps.imageorganizer.adapters.FolderAdapter
import com.rigosapps.imageorganizer.databinding.ActivityMainBinding
import com.rigosapps.imageorganizer.model.Folder
import com.rigosapps.imageorganizer.screens.HomeFragment
import com.rigosapps.imageorganizer.viewModels.FolderViewModel
import com.rigosapps.imageorganizer.viewModels.ItemViewModel
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private lateinit var itemViewModel: ItemViewModel
    private lateinit var folderViewModel: FolderViewModel
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
            val fragmentContainerViewId: Int = binding.homeContainer.id
            supportFragmentManager.beginTransaction().replace(fragmentContainerViewId, mainFragment)
                .commitNow()
        }
        binding.customDrawer.allCard.setOnClickListener() {
            setTitle("All")
            itemViewModel.getAllData()
            supportFragmentManager.beginTransaction()
                .replace(binding.homeContainer.id, HomeFragment.newInstance())
                .commitNow()

        }

        binding.customDrawer.addFolderButton.setOnClickListener() {
            showCreateFolderDialog()

        }

        folderViewModel = ViewModelProvider(this).get(FolderViewModel::class.java)
        //setTitle(folderViewModel.getFolder(id).folderName)

        val folderAdapter = FolderAdapter(::onFolderClick, ::onDelete)
        binding.customDrawer.folderList.adapter = folderAdapter
        binding.customDrawer.folderList.layoutManager = LinearLayoutManager(this)
        folderViewModel.readAllData.observe(this) { folderItems ->
            folderAdapter.setData(folderItems)

        }


            setTitle("All")


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

    private fun onFolderClick(id: Long) {
        itemViewModel.getEntriesByFolder(id)
        setTitle(folderViewModel.getFolder(id).folderName)
        supportFragmentManager.beginTransaction()
            .replace(binding.homeContainer.id, HomeFragment.newInstance())
            .commitNow()


    }

    private fun onDelete(folder: Folder) {


        val dialogTitle = getString(R.string.delete_confirm)
        val positiveButtonTitle = getString(R.string.yes_delete)
        val negativeButtonTitle = getString(R.string.no_delete)
// 2
        val builder = AlertDialog.Builder(this)


        builder.setTitle(dialogTitle)


// 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->


            itemViewModel.deleteImageItemByFolder(folder.key)
            folderViewModel.deleteFolder(folder)


            dialog.dismiss()
            Toast.makeText(
                this,
                "Deleted",
                Toast.LENGTH_SHORT
            ).show()
        }

        builder.setNegativeButton(negativeButtonTitle) { dialog, _ ->

            dialog.dismiss()
        }
// 4
        builder.create().show()

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
        1
        val dialogTitle = getString(R.string.name_of_folder)
        val positiveButtonTitle = getString(R.string.create_list)
// 2
        val builder = AlertDialog.Builder(this)
        val folderTitleText = EditText(this)
        folderTitleText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(folderTitleText)


// 3
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val folder = Folder(
                0,
                folderTitleText.text.toString(),
            )
            folderViewModel.addFolder(folder)


            dialog.dismiss()
        }
// 4
        builder.create().show()


    }


    companion object {
        const val INTENT_LIST_KEY = "imageItem"
        const val LIST_DETAIL_REQUEST_CODE = 10
        const val FOLDER_ID = "folder_id"
    }

}



