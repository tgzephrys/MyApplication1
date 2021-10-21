package com.example.myapplication1.userPage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import kotlinx.android.synthetic.main.activity_user.*
import java.io.File

class UserActivity : AppCompatActivity() {

    private val takePhoto = 1
    private val fromAlbum = 2
    lateinit var imageUri: Uri
    lateinit var outputImage: File

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        supportActionBar?.hide()
        ActivityController.addActivity(this)
        title_bar.initBar(this)

        photoBtn.setOnClickListener{
            chooseView.visibility = View.VISIBLE
        }

        cancelView.setOnClickListener{
            chooseView.visibility = View.INVISIBLE
        }

        takePhotoBtn.setOnClickListener(){
            chooseView.visibility = View.INVISIBLE
            outputImage = File(externalCacheDir, "outPut_image.jpg")
            if(outputImage.exists()){
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                FileProvider.getUriForFile(this, "com.example.myapplication1.fileProvider", outputImage)
            } else{
                Uri.fromFile(outputImage)
            }

            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        fromAlbumBtn.setOnClickListener {
            chooseView.visibility = View.INVISIBLE
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK){
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    userPhoto.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    data.data?.let {
                        val bitmap = getBitmapFromUri(it)
                        userPhoto.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap{
        val exif = ExifInterface(outputImage.path)

        return when (exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotateBitmap
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }
}