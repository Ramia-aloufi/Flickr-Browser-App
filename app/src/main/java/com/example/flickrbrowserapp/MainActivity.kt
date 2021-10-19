package com.example.flickrbrowserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.test.withTestContext
import org.json.JSONObject
import java.lang.Exception
import java.net.URL
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.text.Charsets.UTF_8

data class Item(val img:String,val text:String)
class MainActivity : AppCompatActivity() {
    lateinit var ll:LinearLayout
    lateinit var btn:Button
    lateinit var et:EditText
    lateinit var al:ArrayList<Item>
    var url = ""
    var search = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll = findViewById(R.id.ll)
        btn = findViewById(R.id.button)
        et = findViewById(R.id.editTextTextPersonName)
        al = arrayListOf()
        btn.setOnClickListener {
            search = et.text.toString()
            url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=5cfbf3eb82179f031c7e1b5d82759cdb&tags=$search&format=json&nojsoncallback=1"
            requestAPI()
}
        rv.adapter = MyAdapter(this,al)
        rv.layoutManager = LinearLayoutManager(this)
    }
    fun requestAPI(){
        CoroutineScope(IO).launch{
            val data = async { CheckURL() }.await()

            if(data.isNotEmpty()){
                bindingToView(data)
            }
        }

    }
    fun CheckURL():String{
        var url = ""
        try {
            url = URL(this.url).readText(Charsets.UTF_8)
        }catch (e:Exception){

        }
        return url
    }

    suspend fun bindingToView(data:String){
        withContext(Dispatchers.Main){
            val jsonOpjdct = JSONObject(data)
            val photos = jsonOpjdct.getJSONObject("photos")
            val photo = photos.getJSONArray("photo")
            for (i in 0..50) {
                val title = photo.getJSONObject(i).getString("title").toString()
                val farmID = photo.getJSONObject(i).getString("farm")
                val serverID = photo.getJSONObject(i).getString("server")
                val id = photo.getJSONObject(i).getString("id")
                val secret = photo.getJSONObject(i).getString("secret")
                val img = "https://farm$farmID.staticflickr.com/$serverID/${id}_$secret.jpg"
                al.add(Item(img,title))
                Log.d("photo", "$photo")
            }
            rv.adapter?.notifyDataSetChanged()

        }



    }

    fun showimg(imm:String){
        imageView2.visibility = View.VISIBLE
        Glide.with(this).load(imm).into(imageView2)
        ll.visibility = View.GONE
        rv.visibility = View.GONE

    }
}