package org.techtown.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.techtown.coroutine.databinding.ActivityMainBinding
import java.net.URL


suspend fun loadImage(imageUrl:String): Bitmap {
    val url = URL(imageUrl)
    val stream = url.openStream()
    return BitmapFactory.decodeStream(stream)
}
class MainActivity : AppCompatActivity() {
    val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            buttonDownload.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                progress.visibility = View.VISIBLE
                val url = editUrl.text.toString()
                val bitmap = withContext(Dispatchers.IO) {
                    loadImage(url)
                }
                imageView.setImageBitmap(bitmap)
                progress.visibility = View.GONE
            }
        }
        }
    }
}