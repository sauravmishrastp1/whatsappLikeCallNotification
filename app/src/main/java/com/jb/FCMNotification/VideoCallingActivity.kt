package com.jb.FCMNotification

import android.app.Activity
import android.app.KeyguardManager
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.jb.FCMNotification.firebase.MyFirebaseMessagingService
import kotlinx.android.synthetic.main.activity_video_calling.*

class VideoCallingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_calling)
        MyFirebaseMessagingService.subscribeForTopic("alert")
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            val token = task.result
            if (!TextUtils.isEmpty(token)) {

               // Log.d("token_id", token!!)
            }
        }


        setAction()

        setConditions()
      //  val telephonyManager: TelephonyManager
//        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//        Log.d("token_id", telephonyManager.getDeviceId().toString())
    }

    private fun setConditions() {
        if (intent.getBooleanExtra("isAccept", false)) {
            callConstraint.visibility = View.GONE
            Toast.makeText(this, "call Accepted", Toast.LENGTH_SHORT).show()

        } else {
            callConstraint.visibility = View.VISIBLE
        }
    }

    private fun setAction() {
        image_button_accept_call.setOnClickListener {
            Toast.makeText(this, "call Accepted", Toast.LENGTH_SHORT).show()
            finish()

        }

        image_button_reject_call.setOnClickListener {
            Toast.makeText(this, "Call Rejected", Toast.LENGTH_SHORT).show()
            finish()


        }
        turnScreenOnAndKeyguardOff()

    }
    fun Activity.turnScreenOnAndKeyguardOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
            )
        }

        with(getSystemService(KEYGUARD_SERVICE) as KeyguardManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requestDismissKeyguard(this@turnScreenOnAndKeyguardOff, null)
            }
        }
    }
}