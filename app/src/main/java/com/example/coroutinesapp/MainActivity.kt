package com.example.coroutinesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coroutinesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainActivity : AppCompatActivity() {

    val url = "https://api.adviceslip.com/advice"
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetAdvice.setOnClickListener { getAdvice() }
    }

    private fun getAdvice() {

        CoroutineScope(IO).launch {
            val data = fetchData()

            withContext(Main) {
                val jsonObject = JSONObject(data)
                val advice = jsonObject.getJSONObject("slip").getString("advice")
                binding.tvAdvice.text = advice
            }
        }
    }

    private suspend fun fetchData() : String {
        var response = ""
        try {
            response = URL(url).readText()
        } catch (e: Exception) {

        }
        return response
    }
}