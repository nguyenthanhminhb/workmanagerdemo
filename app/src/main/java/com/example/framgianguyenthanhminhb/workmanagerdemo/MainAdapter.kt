package com.example.framgianguyenthanhminhb.workmanagerdemo

import android.os.Environment
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_main.view.*
import java.io.File


/**
 * Created by MinhNguyen on 27/11/2018.
 * nguyen.thanh.minhb@framgia.com
 */
class MainAdapter(var list: MutableList<ImageModel>,
    var onClick: (url: String, position: Int) -> Unit) : RecyclerView.Adapter<MainAdapter.MainVH>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainVH {
        return MainVH(LayoutInflater.from(p0.context).inflate(R.layout.item_main, p0, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MainVH, p1: Int) {
        p0.setData(list[p1])
    }

    inner class MainVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                onClick.invoke(list[layoutPosition].url, layoutPosition)
            }
        }

        fun setData(imageModel: ImageModel) {
            itemView.tvName.text = imageModel.url
            itemView.tvStatus.text = when (imageModel.status) {
                ImageModel.Status.DONE -> "Done"
                ImageModel.Status.FAILED -> "Failed"
                ImageModel.Status.STARTED -> "Started"
                ImageModel.Status.IN_PROGRESS -> "Downloading"
            }
            if (imageModel.status == ImageModel.Status.DONE) {
                val path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS)
                val file = File(path, imageModel.filePath)
                Glide.with(itemView.context).load(file).into(itemView.ivImage)
                itemView.tvName.text = imageModel.filePath
            } else {
                itemView.ivImage.background = AppCompatResources.getDrawable(itemView.context,
                    R.mipmap.ic_launcher)
                itemView.tvName.text = imageModel.url
            }
        }
    }

    fun updateData(position: Int, filePath: String) {
        list[position].status = ImageModel.Status.DONE
        list[position].filePath = filePath
    }
}