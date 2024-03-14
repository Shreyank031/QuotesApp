package com.shrey.quotes_app_viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.nio.charset.Charset

class MainViewModel(val context: Context) : ViewModel() {
    private var quoteList: Array<Quotes> = emptyArray()
    private var index = 0

    init {
        quoteList = loadQuotesFromAssets()
    }

    private fun loadQuotesFromAssets(): Array<Quotes> {
        //We need application context(Note: We cannot pass views) to read the quotes from quotes.json file.
        // We get context from MainActivity. It will be passed from mainActivity
        //We know that if we want to pass something in viewModel(i.e parameterize the viewModel) we need touse
        //ViewModel factory. So we define a context in 'factory'
        val inputStream = context.assets.open("quotes.json") //open the file
        val size = inputStream.available() //get the size of the file
        val buffer = ByteArray(size) //create a buffer wrt size of the file. Define a buffer
        inputStream.read(buffer) //Read file and store it inside buffer
        inputStream.close()//close the inputStream

        //Not the data is in the form of Byte array and we need to convert it into String.The string will be the Json data
        val json = String(buffer, Charsets.UTF_8)//The byteArray will be encoded to json format.
        //We parse the json using gson
        val gson = Gson()
        return gson.fromJson(json, Array<Quotes>::class.java)

    }

    fun getQuotes() = quoteList[index]

    fun nextQuotes(): Quotes {
        index = (index + 1) % quoteList.size
        return getQuotes()
    }

    fun previousQuotes(): Quotes {
        index = if (index - 1 < 0) {
            quoteList.size - 1
        } else {
            index - 1
        }
        return getQuotes()
    }
}