package dev.syamsu.projects.bench

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.tracing.Trace
import coil.load
import dev.syamsu.projects.bench.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class SimpleRvAdapter : RecyclerView.Adapter<SimpleRvAdapter.VH>() {

    private val items = populateItems()

    private fun populateItems() = List(500) { index ->
        object {
            val imageUrl = "https://picsum.photos/id/$index/5000/3333"
            val title = "Picsum #$index"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        Trace.beginSection("RV onBind load image")
        holder.binding.imageView.load(items[position].imageUrl)
        Trace.endSection()
        Trace.beginSection("RV onBind show hide button")
        // if-else show/hide button
        Trace.endSection()
        holder.binding.tvTitle.text = items[position].title
    }

    inner class VH(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
