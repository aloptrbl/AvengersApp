package com.aloptrbl.avengersapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aloptrbl.avengersapp.adapter.HeroAdapter
import com.aloptrbl.avengersapp.data.Hero
import com.aloptrbl.avengersapp.viewmodel.HeroViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val heroViewModel by viewModels<HeroViewModel>()
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: HeroAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val heroes = listOf(
            Hero(1, "Iron Man", 1.5f, R.drawable.ironman),
            Hero(2, "Captain America", 2.7f, R.drawable.captainamerica),
            Hero(3, "Thor", 3.0f, R.drawable.thor),
            Hero(4, "Hulk", 2.0f, R.drawable.hulk),
            Hero(5, "Black Widow", 1.0f, R.drawable.blackwidow)
        )

        mainScope.launch {
            heroes.forEach {
                heroViewModel.insertCharacters(it)
            }
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = HeroAdapter(heroes, heroViewModel, this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun getDaoCharacters() {
        heroViewModel.getAllCharacters().observe(this, Observer {
            viewAdapter.updateHeroList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        getDaoCharacters()
    }
}