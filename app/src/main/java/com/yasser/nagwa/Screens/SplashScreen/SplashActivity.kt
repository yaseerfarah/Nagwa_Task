package com.yasser.nagwa.Screens.SplashScreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.yasser.nagwa.R
import com.yasser.nagwa.Screens.MainScreen.MainActivity
import com.yasser.nagwa.Utils.PermissionsUtil

class SplashActivity : AppCompatActivity() {
    val PERMISSION_REQUEST_CODE=1234
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            if (PermissionsUtil.hasPermissions(this)){
                MainActivity.startActivity(this)
                finish()
            }else{
                requestPermissions()
            }



        },2000)

    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            PermissionsUtil.permissions,
            PERMISSION_REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionsUtil.permissionsGranted(grantResults)) {
            MainActivity.startActivity(this)
            finish()
        } else finish()
    }


}