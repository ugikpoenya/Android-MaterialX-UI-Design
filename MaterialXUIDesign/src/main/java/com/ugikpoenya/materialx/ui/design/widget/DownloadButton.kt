package com.ugikpoenya.materialx.ui.design.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.ugikpoenya.materialx.ui.design.R

@SuppressLint("UseCompatLoadingForDrawables")
class DownloadButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : CardView(context, attrs, defStyleAttr) {

    private val progressBar: ProgressBar
    private val textView: TextView
    private val statusTextView: TextView
    private val iconImageView: ImageView
    private val btnDownload: RelativeLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.download_button, this, true)
        progressBar = findViewById(R.id.btnDownloadProgress)
        textView = findViewById(R.id.btnDownloadText)
        statusTextView = findViewById(R.id.btnDownloadStatus)
        iconImageView = findViewById(R.id.btnDownloadIcon)
        btnDownload = findViewById(R.id.btnDownload)
    }

    fun init(text: String, progress: Int) {
        progressBar.progress = progress
        if (progress == 100) {
            iconImageView.setImageResource(R.drawable.ic_download_done)
        } else {
            iconImageView.setImageResource(R.drawable.ic_file_download)
        }

        statusTextView.text = ""
        textView.text = text
    }

    fun setProgress(progress: Int) {
        progressBar.progress = progress
        statusTextView.text = "$progress%"
    }

    fun setProgressDrawable(progressDrawable: Drawable?) {
        progressBar.progressDrawable = progressDrawable
    }


    fun getProgress(): Int {
        return progressBar.progress
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun setColor(color:Int) {
        textView.setTextColor(color)
        statusTextView.setTextColor(color)
        iconImageView.setColorFilter(color)
    }

    fun setTextColor(color:Int) {
        textView.setTextColor(color)
    }

    fun setStatusColor(color:Int) {
        statusTextView.setTextColor(color)
    }

    fun setIconColor(color:Int) {
        iconImageView.setColorFilter(color)
    }

    fun setIcon(icon: Int) {
        iconImageView.setImageResource(icon)
    }

    fun setStart() {
        iconImageView.setImageResource(R.drawable.ic_file_download)
        textView.text = "Downloading"
    }

    fun setStop() {
        iconImageView.setImageResource(R.drawable.ic_download_done)
        textView.text = "Downloading Completed"
        progressBar.progress = 100
        statusTextView.text = ""
    }

    fun setOnDownloadClickListener(listener: OnClickListener) {
        btnDownload.setOnClickListener(listener)
    }
}
