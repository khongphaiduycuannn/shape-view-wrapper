package com.ndmquan.test

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ndmquan.permission.PermissionUtils
import com.ndmquan.permission.callback.PermissionCallback

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        findViewById<Button>(R.id.btn).setOnClickListener {
            PermissionUtils(this).requestPermission(
                permission = Manifest.permission.POST_NOTIFICATIONS,
                callback = object : PermissionCallback() {
                    override fun onAllGranted(permission: List<String>) {
                        showToast("Permission granted")
                    }

                    override fun onDenied(
                        listGranted: List<String>,
                        listDenied: List<String>
                    ) {
                        showToast("Permission denied")
                    }

                    override fun onSettingResult(result: Boolean) {
                        if (result) {
                            showToast("Permission granted after setting")
                        } else {
                            showToast("Permission denied after setting")
                        }
                    }
                }
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}