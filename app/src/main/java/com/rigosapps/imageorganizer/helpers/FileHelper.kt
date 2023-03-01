package com.rigosapps.imageorganizer.helpers

import android.R
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import timber.log.Timber
import java.io.*
import java.util.*


/*

Folder class to assist in reading and writing to local storage. I thought it made sense to extract all this
logic and put it on its seperate class in order to make it clearer. This class uses compressed bitmaps to write to internal
storage. The room DB ImageItem stores the path where the image is stored and it is retrieved using the loadImageFromStorage
method.

 */

class FileHelper {

    companion object {

        const val DIRECTORY_NAME = "imageDir"



        //Use the below code to save the image to internal directory.

        fun saveToInternalStorage(bitmapImage: Bitmap, context: Context): String {
            val cw = ContextWrapper(context.applicationContext);
            // path to /data/data/yourapp/app_data/imageDir
            val directory = cw.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);
            // Create imageDir
            // Create imageDir

           val name = UUID.randomUUID().toString()
            val mypath = File(directory, "$name.jpg");

            lateinit var fos: FileOutputStream
            try {
                fos = FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (e: Exception) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (e: IOException) {
                    e.printStackTrace();
                }
            }
            Timber.e("saving image to ${mypath.absolutePath}")
            return mypath.absolutePath;
        }

        fun loadImageFromStorage(path: String): Bitmap? {

            lateinit var fis: FileInputStream
            lateinit var bitmap: Bitmap
            try {
                val f = File(path)
                fis = FileInputStream(f)

                bitmap = BitmapFactory.decodeStream(fis)


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                return null
            } finally {


                try {
                    fis.close();
                } catch (e: IOException) {
                    e.printStackTrace();
                }


            }
            return bitmap
        }

    }
}

