package com.rigosapps.imageorganizer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.rigosapps.imageorganizer.databinding.ActivityListDetailBinding
import com.rigosapps.imageorganizer.screens.ListDetailFragment
import com.rigosapps.imageorganizer.viewModels.ImageItem
import com.rigosapps.imageorganizer.viewModels.ListDetailViewModel
import timber.log.Timber


class ListDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityListDetailBinding
    lateinit var viewModel: ListDetailViewModel

    var permissionGranted = false

    companion object {

        const val REQUEST_CAMERA_AND_STORAGE = 249


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ListDetailViewModel::class.java)


        val imageItem = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY, ImageItem::class.java)
        } else {
            intent.getParcelableExtra<ImageItem>(MainActivity.INTENT_LIST_KEY)
        }

        viewModel.imageItem = imageItem!!

        setTitle(viewModel.imageItem.title)
        requestPermissions()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.detailContainer, ListDetailFragment.newInstance()).commitNow()


        }
    }


    private fun requestPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            ) permissionGranted = true;


        } else


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
            MainActivity.INTENT_LIST_KEY, viewModel.imageItem
        )
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}