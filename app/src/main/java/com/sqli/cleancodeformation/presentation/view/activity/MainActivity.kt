package com.sqli.cleancodeformation.presentation.view.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.sqli.cleancodeformation.R
import dagger.hilt.android.AndroidEntryPoint

@Suppress("PrivatePropertyName")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    private val PERMISSION_CALL = 1

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_graph) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE,
            )
        }

        if (!packageManager.hasSystemFeature("PERMISSION_CALL")) {
            requestPermissions(
                arrayOf(android.Manifest.permission.CALL_PHONE),
                PERMISSION_CALL,
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    PERMISSION_CALL,
                )
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
