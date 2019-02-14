package com.example.framgianguyenthanhminhb.workmanagerdemo

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by MinhNguyen on 27/11/2018.
 * nguyen.thanh.minhb@framgia.com
 */
class DownloadWorker(context: Context, workerParameters: WorkerParameters) : Worker(context,
    workerParameters) {

    private var downloadManager: DownloadManager = context.getSystemService(
        Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun doWork(): Result {
        return try {
            val url = inputData.getString(MY_KEY)
            val pos = inputData.getInt(DOWNLOAD_POSITION, 0)
            val request = DownloadManager.Request(Uri.parse(url))
            request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedOverRoaming(false)
            request.setVisibleInDownloadsUi(true)
            val fileName = "${System.currentTimeMillis()}.png"
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            val refId = downloadManager.enqueue(request)
            val output = Data.Builder()
            output.putInt(DOWNLOAD_POSITION, pos)
            output.putString(FILE_PATH, fileName)
            Log.d("hehehe", "Download SUCCESS $refId")
            Result.success(output.build())
        } catch (e: Exception) {
            Log.d("hehehe", "Download FAIL")
            Result.failure()
        }
    }
}