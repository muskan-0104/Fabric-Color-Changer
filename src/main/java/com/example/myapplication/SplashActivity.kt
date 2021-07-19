package com.example.myapplication

import android.content.Intent
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.Manifest
import androidx.core.app.ActivityCompat
import android.view.animation.AnimationUtils
import android.widget.TextView


class SplashActivity : AppCompatActivity() {

    //permissions
    var permissionsString = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //animation
        val backgrd:TextView=findViewById(R.id.Come)
        val slideAnimation=AnimationUtils.loadAnimation(this,R.anim.side_slide)
        backgrd.startAnimation(slideAnimation)



        if (!hasPermissions(this@SplashActivity, *permissionsString)) {
            ActivityCompat.requestPermissions(
                this@SplashActivity, permissionsString,
                131
            )
        } else {
            Handler().postDelayed({
                val startAct = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(startAct)
                this.finish()
            }, 2000)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out
        String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    Handler().postDelayed({
                        val startAct = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(startAct)
                        this.finish()
                    }, 1500)
                } else {
                    Toast.makeText(
                        this@SplashActivity,
                        "Please grant all the permissions to continue.",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(
                    this@SplashActivity, "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
                this.finish()
                return
            }
        }
    }


    fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        var hasAllPermissions = true
        for (permission in permissions) { /*for loop to check for every single permission*/
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasAllPermissions = false
            }
        }
        return hasAllPermissions
    }
}


