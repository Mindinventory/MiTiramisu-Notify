package com.mi.mitiramisunotify.presentation.notification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mi.mitiramisunotify.BuildConfig
import com.google.android.material.snackbar.Snackbar
import com.mi.mitiramisunotify.R

class NotificationWithXMLActivity : AppCompatActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // make your action here
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_with_xml)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Snackbar.make(
                    findViewById(R.id.parent_layout),
                    getString(R.string.notification_permission_required),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.go_to_settings)) {
                    // Responds to click on the action
                    val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = uri
                        startActivity(this)
                    }
                }.show()
            }

            else -> {
                // The registered ActivityResultCallback gets the result of this request
                requestPermissionLauncher.launch(permission)
            }
        }
    }
}
