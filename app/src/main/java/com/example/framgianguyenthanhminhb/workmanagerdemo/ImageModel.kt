package com.example.framgianguyenthanhminhb.workmanagerdemo

/**
 * Created by MinhNguyen on 27/11/2018.
 * nguyen.thanh.minhb@framgia.com
 */
class ImageModel(val url: String, var progress: Int, var status: Status,
    var filePath: String = "") {

    enum class Status {
        DONE, FAILED, IN_PROGRESS, STARTED
    }
}