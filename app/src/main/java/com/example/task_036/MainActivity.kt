package com.example.task_036

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.task_036.util.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imageViewIV: ImageView
    private lateinit var loadBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        setSupportActionBar(toolbar)
        title = "Загрузчик картинок"

        loadBTN.setOnClickListener{
            getRequest()
        }
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    private fun getRequest() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {
                RetrofitInstance.api.getRandomDog()
            } catch (e: IOException) {
                Toast.makeText(applicationContext, "app error ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(applicationContext, "http error ${e.message}", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext, "200 ${response.code()}", Toast.LENGTH_LONG).show()
                    val data = response.body()
                    Picasso.get().load(data!!.url.toString()).into(imageViewIV)
                }
            }
        }
    }
    
    private fun initUI() {
        toolbar = findViewById(R.id.toolbar)
        imageViewIV = findViewById(R.id.imageViewIV)
        loadBTN = findViewById(R.id.loadBTN)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain->{
                moveTaskToBack(true);
                exitProcess(-1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}