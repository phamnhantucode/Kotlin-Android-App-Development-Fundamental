package com.example.wallpaperapplicitation

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallpaperapplicitation.imagecategories.Category
import com.example.wallpaperapplicitation.imagecategories.CategoryAdapter
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    var categoryList: MutableList<Category> = ArrayList<Category>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categoryRecyclerView = findViewById<RecyclerView>(R.id.categories)
        categoryRecyclerView.setHasFixedSize(true)
        categoryRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)

        val assetManager = assets

        try {
            var natureIs: InputStream = assetManager.open("nature1.jpg")
            val natureCategoryImg: Bitmap = BitmapFactory.decodeStream(natureIs)
            categoryList.add(Category("Nature", natureCategoryImg))

            var planetIs: InputStream = assetManager.open("planet1.jpg")
            val planetCategoryImg: Bitmap = BitmapFactory.decodeStream(planetIs)
            categoryList.add(Category("Planet", planetCategoryImg))

            var cityIs: InputStream = assetManager.open("city1.jpg")
            val cityCategoryImg: Bitmap = BitmapFactory.decodeStream(cityIs)
            categoryList.add(Category("City", cityCategoryImg))

            categoryRecyclerView.adapter = CategoryAdapter(categoryList, this@MainActivity)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}