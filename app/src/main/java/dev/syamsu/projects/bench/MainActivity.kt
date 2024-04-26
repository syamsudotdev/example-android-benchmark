package dev.syamsu.projects.bench

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.tracing.Trace
import dev.syamsu.projects.bench.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Trace.beginSection(TAG_INFLATE_VIEW)
        // set content view with view binding
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Trace.endSection()
        // set window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(viewBinding.mainRv.id)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Trace.beginSection(TAG_ATTACH_ADAPTER)
        // set recycler view adapter
        viewBinding.mainRv.layoutManager = LinearLayoutManager(this)
        viewBinding.mainRv.adapter = SimpleRvAdapter()
        Trace.endSection()
    }

    companion object {
        const val TAG_INFLATE_VIEW = "MainActivity InflateView"
        const val TAG_ATTACH_ADAPTER = "MainActivity AttachAdapter"
    }
}
