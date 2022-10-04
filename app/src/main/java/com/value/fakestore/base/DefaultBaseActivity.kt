package com.value.fakestore.base

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.data.cache.source.UserModule
import javax.inject.Inject


abstract class DefaultBaseActivity : AppCompatActivity {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    @Inject
    lateinit var userModule: UserModule

    companion object {
        private const val KEY_ACTIVITY_SAVED_STATE = "activity_saved_state"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        val savedState = savedInstanceState ?: intent?.getBundleExtra(KEY_ACTIVITY_SAVED_STATE)
        intent?.removeExtra(KEY_ACTIVITY_SAVED_STATE)
        super.onCreate(savedState)
        resetTitle()

//        KeyboardUtils.fixAndroidBug5497(this)
//        KeyboardUtils.fixSoftInputLeaks(this)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        val view = currentFocus
        val ret = super.dispatchTouchEvent(event)
        if (view is EditText) {
            val w = currentFocus
            val scrcoords = IntArray(2)
            if (w == null) return false
            w.getLocationOnScreen(scrcoords)
            val x = event.rawX + w.left - scrcoords[0]
            val y = event.rawY + w.top - scrcoords[1]
            if (event.action == MotionEvent.ACTION_UP && (x < w.left || x >= w.right
                        || y < w.top || y > w.bottom)
            ) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
            }
        }
        return ret
    }

    /**
     * updates the toolbar text locale if it set from the android:label property of Manifest
     */
    private fun resetTitle() {
        try {
            val label = packageManager.getActivityInfo(
                componentName,
                PackageManager.GET_META_DATA
            ).labelRes
            if (label != 0) {
                setTitle(label)
            }
        } catch (e: Exception) {
        }
    }

    override fun recreate() {
        try {
            restart()
        } catch (ex: Exception) {
            super.recreate()
        }
    }

    open fun restart() {
        val bundle = Bundle()
//        onSaveInstanceState(bundle)
        if (intent == null) {
            intent = Intent(this, javaClass)
        }
        intent.putExtra(KEY_ACTIVITY_SAVED_STATE, bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    open fun restartApp() {
        val pm = baseContext.packageManager
        if (pm != null) {
            val launchIntent = pm.getLaunchIntentForPackage(baseContext.packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val pendingIntentId = 223344
                val pendingIntent = PendingIntent.getActivity(
                    this,
                    pendingIntentId,
                    launchIntent,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        PendingIntent.FLAG_IMMUTABLE
                    else
                        PendingIntent.FLAG_CANCEL_CURRENT
                )
//                val mgr = getSystemService(ALARM_SERVICE) as AlarmManager
//                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent)
                finishAffinity()
                startActivity(launchIntent)
            }
        }
    }
}
