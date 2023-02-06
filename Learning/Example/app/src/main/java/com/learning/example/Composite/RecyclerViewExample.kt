package com.learning.example.Composite

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.learning.example.R

class RecyclerViewExample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_example)

        initView()
    }

    fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.HORIZONTAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.itemAnimator = DefaultItemAnimator()
        val dataShop = ArrayList<DataShop>()
        dataShop.add(DataShop(R.drawable.android_icon, "Android icon"))
        dataShop.add(DataShop(R.drawable.java_icon, "Java icon"))
        recyclerView.adapter = ShopAdapter(dataShop, this)
    }

}

class DataShop {
    val imageId: Int
    val name: String

    constructor(imageId: Int, name: String) {
        this.imageId = imageId
        this.name = name
    }
}

class ShopAdapter : RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    var data : ArrayList<DataShop>
    var context : Context

    constructor(data: ArrayList<DataShop>, context: Context) : super() {
        this.data = data
        this.context = context
    }

    class ViewHolder: RecyclerView.ViewHolder {
        val imageView: ImageView
        val textView: TextView

        constructor(itemView: View) : super(itemView) {
            this.imageView = itemView.findViewById(R.id.imageView)
            this.textView = itemView.findViewById(R.id.textView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ShopAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row_recycler_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(data[position].imageId)
        holder.textView.text = (data[position].name)

        holder.itemView.setOnClickListener {
            data.removeAt(position)
            notifyItemRemoved(position)

            Toast.makeText(context, holder.textView.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}
