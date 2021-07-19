package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File


var filepath: Uri? =null
var file: File? =null
val REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {
    lateinit var filepath: Uri

    @SuppressLint("WrongViewCast")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //listening to button to add photo
        var but: Button = findViewById(R.id.ChoosePhoto)
        but?.setOnClickListener {
           openGalleryForImage()
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            var txt:TextView=findViewById(R.id.Come)
            txt.text="Image Uploaded successfully!"
            var img:ImageView=findViewById(R.id.imageView)
            var display: Uri? =data?.data
            img.setImageURI(display) // handle chosen image
            img.visibility=1

            var next:ImageView=findViewById(R.id.nxt)
            next.setOnClickListener{
                nextPage(display)
            }

        }
    }
    private fun nextPage(display: Uri?) {
        var clicklisten = Intent(this@MainActivity, ImageDisplay::class.java)
        clicklisten.putExtra("my_image",display.toString())
        startActivity(clicklisten)
        finish()
    }

}


