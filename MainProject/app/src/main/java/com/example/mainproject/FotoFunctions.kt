package com.example.mainproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.checkSelfPermission
import java.io.File
import java.io.FileOutputStream

public val CAPTURE_PHOTO = 104
public val REQUEST_PERM_WRITE_STORAGE = 102

//image pick code
public val IMAGE_PICK_CODE = 1000;
//Permission code
public val PERMISSION_CODE = 1001;


//Принимает битмап и сохраняет его во внешнюю директорию, дело происходит в активити_1
fun saveImageExternal(finalBitmap: Bitmap?, file_external: File, activity_1: Activity) {
    if (file_external.exists()) file_external.delete()
    try {
        val out = FileOutputStream(file_external)//тут создался файл!!!!!!
        finalBitmap?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

//Принимает битмап и сохраняет его во внутреннюю директорию, дело происходит в активити_1
fun saveImageInternal(finalBitmap: Bitmap?, file_internal: File, activity_1: Activity) {
    if (file_internal.exists()) file_internal.delete()
    try {
        val out = FileOutputStream(file_internal)//тут создался файл!!!!!!
        finalBitmap?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

//фоткает
fun takePhotoByCamera(activity_1: Activity) {
    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    activity_1.startActivityForResult(cameraIntent, CAPTURE_PHOTO)
}

//принимает файл с картинкой и устанавлиет его в выбранном ImageVeiw
fun getImage(imageView: ImageView, file: File, activity_1: Activity) {
    //val out = FileOutputStream(file)
    try {
        val myuri = Uri.fromFile(file)
        imageView.setImageURI(myuri)
    } catch (e:java.lang.Exception){
        Toast.makeText(activity_1, "Problem!", Toast.LENGTH_LONG).show()
    }

}

//Устанавливает на экран то, что отфоткали
fun captured_bitmap (requestCode: Int, resultCode: Int, returnIntent: Intent?, bitmap_current: Bitmap?,
imgv_capture_image_preview: ImageView) {
    var bitmap_1 = bitmap_current

    if (resultCode == Activity.RESULT_OK) {
        when (requestCode) {

            CAPTURE_PHOTO -> {

                val capturedBitmap = returnIntent?.extras!!.get("data") as Bitmap
                bitmap_1 = capturedBitmap
                imgv_capture_image_preview.setImageBitmap(capturedBitmap)
            }
            else -> {
            }
        }

    }
}

fun pickImageFromGallery(activity_1: Activity) {
    //Intent to pick image
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    activity_1.startActivityForResult(intent, IMAGE_PICK_CODE)
}



