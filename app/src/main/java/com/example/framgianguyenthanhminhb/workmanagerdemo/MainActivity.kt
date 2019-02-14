package com.example.framgianguyenthanhminhb.workmanagerdemo

import android.Manifest
import android.app.DownloadManager
import android.app.NotificationManager
import android.arch.lifecycle.Observer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var downloadManager: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        if (!isStoragePermissionGranted()) {

        }
        setContentView(R.layout.activity_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(getArrayRandomImage(5)) { url, pos ->
            val inputData = Data.Builder()
            inputData.putString(MY_KEY, url)
            inputData.putInt(DOWNLOAD_POSITION, pos)
            val task = OneTimeWorkRequest.Builder(
                DownloadWorker::class.java).setInputData(
                inputData.build()).build()
            WorkManager.getInstance().beginWith(task).enqueue()
            WorkManager.getInstance().getWorkInfoByIdLiveData(task.id).observe(this, Observer {
                if (it?.state == WorkInfo.State.SUCCEEDED)
                    adapter.updateData(it.outputData.getInt(DOWNLOAD_POSITION, 0),
                        it.outputData.getString(FILE_PATH) ?: "")
            })
        }
        rvMain.adapter = adapter

    }

    private var onComplete: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(ctxt: Context, intent: Intent) {

            val referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.d("hehehe", "referenceId = $referenceId")
            val mBuilder = NotificationCompat.Builder(this@MainActivity).setSmallIcon(
                R.mipmap.ic_launcher)
                .setContentTitle("Workmanager demo")
                .setContentText("All Download completed")

            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(455, mBuilder.build())
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(onComplete)
    }

    private fun isStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                return false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission granted
        }
    }
}
