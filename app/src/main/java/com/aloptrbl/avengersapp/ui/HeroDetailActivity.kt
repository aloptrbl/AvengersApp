package com.aloptrbl.avengersapp.ui

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.aloptrbl.avengersapp.R
import com.aloptrbl.avengersapp.viewmodel.HeroViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HeroDetailActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var ratingBar: RatingBar

    private val heroViewModel by viewModels<HeroViewModel>()
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)

        initLayout()
    }

    private fun initLayout() {
        backButton = findViewById<Button>(R.id.backButton)
        title = findViewById<TextView>(R.id.title)
        image = findViewById<ImageView>(R.id.image)
        ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val intent = intent

        val heroName = intent.getStringExtra("HERO_NAME")
        val heroImage = intent.getIntExtra("HERO_IMAGE", 0)

        heroName?.let {
            heroViewModel.getRatingByTitle(it).observe(this, Observer { rating ->
                if (rating != null) {
                    ratingBar.rating = rating
                }
            })
        }

        title.text = heroName
        heroImage.let { image.setImageResource(it) }

        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            mainScope.launch {
                heroName?.let { heroViewModel.updateRating(it, rating) }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}