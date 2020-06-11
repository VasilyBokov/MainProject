package com.example.mainproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.android.synthetic.main.activity_my_profile.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream

class MyProfileActivity : AppCompatActivity() {
    var bitmap_current: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        val path = this.getFilesDir()
        val file_internal = File(path, "ImageInImage.jpg")
        if (file_internal.exists()) getImage(profile_image, file_internal,this)
        val Profile_description_1 = File(path, "Profile_description_1.txt")
        val Profile_description_2 = File(path, "Profile_description_2.txt")
        var str: String = ""

        if (Profile_description_1.exists()) {
            Log.d("SUKA", "777777")
            str = FileInputStream(Profile_description_1).bufferedReader().use { it.readText() }
            profile_description_1.setText(str)
        }
        if (Profile_description_2.exists()) {
            str = FileInputStream(Profile_description_2).bufferedReader().use { it.readText() }
            profile_description_2.setText(str)
        }



        to_main_activity.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        save_profile_changes.setOnClickListener() {
            //сохраняем изменения описаний
            update_text_files(
                listOf("Profile_description_1.txt", "Profile_description_2.txt"),
                listOf(
                    profile_description_1.text.toString(),
                    profile_description_2.text.toString()
                ), this
            )
            //сохраняем фотку во внутреннюю директорию
            saveImageInternal(bitmap_current,file_internal,this)
        }
        //Просто ставит фотку из галереи в ImageView
        change_profile_foto_gallery.setOnClickListener() {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//хз, зачем это писать
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery(this);
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery(this);
            }
        }

        make_foto.setOnClickListener() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MyProfileActivity, arrayOf(android.Manifest.permission.CAMERA), 1)
                }

            }
            if (ActivityCompat.checkSelfPermission(applicationContext,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this@MyProfileActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERM_WRITE_STORAGE)

            } else {
                takePhotoByCamera(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery(this)
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            IMAGE_PICK_CODE ->
            {
                if (resultCode == Activity.RESULT_OK){
                    profile_image.setImageURI(data?.data)
                    val capturedBitmap = profile_image.drawable.toBitmap()
                    bitmap_current = capturedBitmap
                }
            }
            CAPTURE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK){
                    val capturedBitmap = data?.extras!!.get("data") as Bitmap
                    bitmap_current = capturedBitmap
                    profile_image.setImageBitmap(capturedBitmap)
                }
            }
            else -> {
            }
        }
    }
}

