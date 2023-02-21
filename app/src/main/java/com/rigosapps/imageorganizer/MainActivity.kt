package com.rigosapps.imageorganizer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.databinding.ActivityMainBinding
import com.rigosapps.imageorganizer.screens.HomeFragment
import com.rigosapps.imageorganizer.screens.ListDetailFragment
import com.rigosapps.imageorganizer.model.ImageItem
import com.rigosapps.imageorganizer.viewModels.ItemViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ItemViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        val navHostFragment =
//            supportFragmentManager.binding as NavHostFragment
//        val navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
        if (savedInstanceState == null) {

            // 1
            val mainFragment = HomeFragment.newInstance()

            // 2
            val fragmentContainerViewId: Int = if (binding.mainFragmentContainer == null) {
                R.id.detail_Container
            } else {
                R.id.main_fragment_container
            }
            supportFragmentManager.beginTransaction()
                .replace(fragmentContainerViewId, mainFragment).commitNow()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {

                viewModel.updateList(data.getParcelableExtra(INTENT_LIST_KEY)!!)

            }
        }
    }

    public fun showListDetail(imageItem: ImageItem) {

        //using normal layout
        if (binding.mainFragmentContainer == null) {
            val listDetailIntent = Intent(
                this,
                ListDetailActivity::class.java
            )
            listDetailIntent.putExtra(INTENT_LIST_KEY, imageItem)
            startActivityForResult(
                listDetailIntent,
                LIST_DETAIL_REQUEST_CODE
            )
        }
        //using the larger layout
        else {
            val bundle = bundleOf(INTENT_LIST_KEY to imageItem)
            val frag = ListDetailFragment()


            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment_container,
                    ListDetailFragment::class.java, bundle, null)
            }
        }


    }





    companion object {
        const val INTENT_LIST_KEY = "imageItem"
        const val LIST_DETAIL_REQUEST_CODE = 10
    }
}