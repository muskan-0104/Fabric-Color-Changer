package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

var red=0
var green=0
var blue=0

class ImageDisplay : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)
        //back button
        var back:ImageView=findViewById(R.id.back)
        back.setOnClickListener{
            var clicklisten = Intent(
                this@ImageDisplay,
                MainActivity::class.java
            )
            startActivity(clicklisten)
            finish()
        }

        var gallery:ImageView=findViewById(R.id.toGallery)
        gallery.setOnClickListener{
            var clicklisten = Intent(
                this@ImageDisplay,
                GalleryScreen::class.java
            )
            startActivity(clicklisten)
        }


        var img:ImageView= findViewById(R.id.Display)
        var greyimg:ImageView=findViewById(R.id.DisplayGrey)
        var imgD: Drawable? =null
        var image_view:ImageView=findViewById(R.id.image_view)
        var saveButton:ImageView=findViewById(R.id.save)
        var tv_code:TextView=findViewById(R.id.tv_code)
        var tv_value:TextView=findViewById(R.id.tv_value)
        var myIntent=getIntent()
        // if bundle is not null then get the image value
        if (myIntent != null)
        {
            var imge=myIntent.getStringExtra("my_image")
            var myuri:Uri?=Uri.parse(imge)
            img.setImageURI(myuri)
            //set 2nd imagegrey
            greyimg.setImageURI(myuri)
            greyimg.toGreyScale()

        }
        //Color Picker Section
        lateinit var  bitmap:Bitmap
        image_view.isDrawingCacheEnabled=true
        image_view.buildDrawingCache(true)

        image_view.setOnTouchListener { v, event ->
            if(event.action==MotionEvent.ACTION_DOWN||event.action==MotionEvent.ACTION_MOVE)
            {
                bitmap=image_view.drawingCache
                val pixel=bitmap.getPixel(event.x.toInt(),event.y.toInt())

                //Getting RGB value from touched pixel
                var r=Color.red(pixel)
                var g=Color.green(pixel)
                var b=Color.blue(pixel)

                //Getting Hex value
                val hex="#"+Integer.toHexString(pixel)
                tv_code.text=hex
                tv_value.text="RGB("+r+","+g+","+b+")"

                img.setImageBitmap(CaptureScreenShot(greyimg))
                img.setColorFilter(pixel,PorterDuff.Mode.SCREEN)

                //savePhoto
                var ss2=CaptureScreenShot(img)
                saveButton.setOnClickListener{
                    saveInGallery(ss2)
                }
            }
            true

        }
    }
    fun ImageView.toGreyScale(){
        val brightness = 10f
        val colorMatrix = floatArrayOf(
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0.33f, 0.33f, 0.33f, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        )
        colorFilter = ColorMatrixColorFilter(colorMatrix)
    }

    fun CaptureScreenShot(img: ImageView): Bitmap? {
        //screenshot
        var screenshot:Bitmap?=null
        try {
            screenshot= Bitmap.createBitmap(img.measuredWidth,img.measuredHeight,Bitmap.Config.ARGB_8888)
            val canvas=Canvas(screenshot)
            img.draw(canvas)
        }catch (e:java.lang.Exception)
        {
            Toast.makeText(this,"Cannot take ss",Toast.LENGTH_SHORT).show()
        }
        return screenshot
    }

    private fun saveInGallery(screenshot: Bitmap?) {

        //Savingpart
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            screenshot?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

}





