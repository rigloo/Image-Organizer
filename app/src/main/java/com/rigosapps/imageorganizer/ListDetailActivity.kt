package com.rigosapps.imageorganizer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.databinding.ActivityListDetailBinding
import com.rigosapps.imageorganizer.screens.ListDetailFragment
import com.rigosapps.imageorganizer.viewModels.ItemViewModel
import com.rigosapps.imageorganizer.viewModels.ListDetailViewModel
import timber.log.Timber


class ListDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityListDetailBinding
    lateinit var viewModelDetail: ListDetailViewModel
    lateinit var viewModelMain: ItemViewModel


    var permissionGranted = false

    companion object {

        const val REQUEST_CAMERA_AND_STORAGE = 249


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        onBackPressed()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setContentView(binding.root)

        viewModelDetail = ViewModelProvider(this).get(ListDetailViewModel::class.java)
        viewModelMain = ViewModelProvider(this).get(ItemViewModel::class.java)

        val bdl = intent.extras
        val id = bdl!!.getLong(MainActivity.INTENT_LIST_KEY)

        viewModelDetail.imageItem = viewModelMain.getImageItem(id)



        setTitle(viewModelDetail.imageItem.title)
        requestPermissions()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailContainer, ListDetailFragment.newInstance()).commitNow()


        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }


    private fun requestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) permissionGranted = true;

        }



        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ), REQUEST_CAMERA_AND_STORAGE
        )

    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)



        if (requestCode == REQUEST_CAMERA_AND_STORAGE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true;

            } else {
                Timber.e("Permission to use camera or storage not granted not granted.")
                showAlert("Do not have permission to use the camera.")
            }
        }

    }


    fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton("Ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        Timber.e("OnBackPressed")
        val bundle = Bundle()
        bundle.putParcelable(
            MainActivity.INTENT_LIST_KEY, viewModelDetail.imageItem
        )
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}