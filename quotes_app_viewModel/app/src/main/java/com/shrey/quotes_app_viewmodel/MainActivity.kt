package com.shrey.quotes_app_viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.shrey.quotes_app_viewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)

        setQuote(mainViewModel.getQuotes())
    }

    fun setQuote(quote: Quotes) {
        binding.txtQuote.text = quote.text
        binding.txtAuthorName.text = quote.author
    }

    fun onPrevious(view: View) {
        setQuote(mainViewModel.previousQuotes())

    }

    fun onNext(view: View) {
        setQuote(mainViewModel.nextQuotes())
    }

    fun onShare(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuotes().text)
        startActivity(intent)

    }
}
