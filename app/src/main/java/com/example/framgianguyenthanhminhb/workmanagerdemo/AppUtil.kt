package com.example.framgianguyenthanhminhb.workmanagerdemo

import java.util.*

const val DOWNLOAD_URL = "DOWNLOAD_URL"
const val DOWNLOAD_POSITION = "DOWNLOAD_POSITION"
const val FILE_PATH = "FILE_PATH"
const val DOWNLOAD_ID = "DOWNLOAD_ID"

val imageStocks = mutableListOf(
    "https://unsplash.com/photos/sOGYhwV-B2A/download?force=true",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-2_044126655.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-3_044126905.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-4_044127014.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-5_044127233.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-6_044127357.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-7_044127482.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-8_044127576.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-9_044127685.jpg",
    "http://thuthuatphanmem.vn/uploads/2018/09/11/hinh-anh-dep-10_044127763.jpg"
)

fun getRandomImage(): String {
    return imageStocks[Random().nextInt(imageStocks.size)]
}

fun getArrayRandomImage(size: Int, isRandom: Boolean): MutableList<ImageModel> {
    val list = mutableListOf<ImageModel>()
    for (i in 0 until size) {
        if (isRandom)
            list.add(ImageModel(getRandomImage(), ImageModel.Status.STARTED))
        else
            list.add(ImageModel("https://unsplash.com/photos/sOGYhwV-B2A/download?force=true",
                ImageModel.Status.STARTED))
    }
    return list
}
