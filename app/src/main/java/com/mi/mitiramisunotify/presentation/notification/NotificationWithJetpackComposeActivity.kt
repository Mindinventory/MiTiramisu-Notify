package com.mi.mitiramisunotify.presentation.notification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.mi.mitiramisunotify.BuildConfig
import com.mi.mitiramisunotify.R
import com.mi.mitiramisunotify.presentation.ui.theme.NotificationTheme

class NotificationWithJetpackComposeActivity : ComponentActivity() {

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // make your action here
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied.
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scaffoldState = rememberScaffoldState()
            NotificationTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                ) { paddingValues ->
                    Box(
                        Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            CheckNotificationPermission()
                        }
                        Text(
                            text = getString(R.string.detail_screen),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun CheckNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(
                this, permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                // make your action here
            }

            shouldShowRequestPermissionRationale(permission) -> {
                AlertDialog(
                    onDismissRequest = { },
                    text = { Text(text = getString(R.string.notification_permission_required)) },
                    confirmButton = {
                        TextButton(onClick = {
                            val uri: Uri =
                                Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                data = uri
                                startActivity(this)
                            }
                        }) { Text(text = getString(R.string.go_to_settings)) }
                    },
                )
            }

            else -> {
                requestNotificationPermission.launch(permission)
            }
        }
    }
}
