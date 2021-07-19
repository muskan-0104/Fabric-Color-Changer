package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class GalleryScreen : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_screen)
        var x=findViewById<TextView>(R.id.txt)
        x.setText("Hey You are in gallery! No Photos yet, add some!")
        var back=findViewById<ImageView>(R.id.back)
        back=findViewById(R.id.back)
        back.setOnClickListener{
            var clicklisten = Intent(
                this@GalleryScreen,
                ImageDisplay::class.java
            )
            startActivity(clicklisten)
            finish() }

        //getImages()

    }

}
