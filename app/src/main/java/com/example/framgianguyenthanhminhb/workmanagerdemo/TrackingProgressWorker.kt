package com.example.framgianguyenthanhminhb.workmanagerdemo

import android.app.DownloadManager
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by MinhNguyen on 15/02/2019.
 * nguyen.thanh.minhb@framgia.com
 */
class TrackingProgressWorker(val context: Context, workerParameters: WorkerParameters) : Worker(
    context, workerParameters) {

    private var downloadManager: DownloadManager = context.getSystemService(
        Context.DOWNLOAD_SERVICE) as DownloadManager

    override fun doWork(): Result {
        val downloadId = inputData.getLong(DOWNLOAD_ID, -1)
        return if (downloadId != -1L) {
            val progress = trackingProgress(downloadId)
            if (progress < 100) {

                LiveDataHelper.getInstance().updatePercentage(progress)
                Log.d("hehehe", "Tr retry")
                Result.retry()
            } else {
                Log.d("hehehe", "Tr ss")
                Result.success()
            }
        } else {
            Log.d("hehehe", "Tr FAI $downloadId")
            Result.failure()
        }
    }

    private fun trackingProgress(downloadId: Long): Double {
        val query = DownloadManager.Query()
        query.setFilterById(downloadId)

        val c = downloadManager.query(query)
        if (c.moveToFirst()) {
            val sizeIndex = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
            val downloadedIndex = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
            val size = c.getInt(sizeIndex)
            val downloaded = c.getInt(downloadedIndex)
            var progress = 0.0
            if (size != -1) progress = downloaded * 100.0 / size
            return progress
        }

        return 0.0
    }
}