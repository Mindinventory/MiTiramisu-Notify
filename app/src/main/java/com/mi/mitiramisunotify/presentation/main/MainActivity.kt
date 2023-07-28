package com.mi.mitiramisunotify.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mi.mitiramisunotify.R
import com.mi.mitiramisunotify.presentation.notification.NotificationWithJetpackComposeActivity
import com.mi.mitiramisunotify.presentation.notification.NotificationWithXMLActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_xml).setOnClickListener {
            startActivity(Intent(this, NotificationWithXMLActivity::class.java))
        }
        findViewById<Button>(R.id.btn_jetpack).setOnClickListener {
            startActivity(Intent(this, NotificationWithJetpackComposeActivity::class.java))
        }
    }
}
