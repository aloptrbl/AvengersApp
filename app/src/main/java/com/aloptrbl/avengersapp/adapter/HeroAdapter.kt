package com.aloptrbl.avengersapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.aloptrbl.avengersapp.R
import com.aloptrbl.avengersapp.data.Hero
import com.aloptrbl.avengersapp.ui.HeroDetailActivity
import com.aloptrbl.avengersapp.viewmodel.HeroViewModel

class HeroAdapter(private var heroes: List<Hero>,private val heroViewModel: HeroViewModel,private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() {

    var heroesList: List<Hero> = heroes

    inner class HeroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.hero_name)
        val image: ImageView = view.findViewById(R.id.hero_image)
        val status: TextView = view.findViewById(R.id.rating_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hero_item, parent, false)
        return HeroViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val hero = heroesList[position]
        holder.name.text = hero.title
        holder.image.setImageResource(hero.image)

        heroViewModel.getRatingByTitle(hero.title).observe(lifecycleOwner, Observer { rating ->
            if (rating != null) {
                val ratingText = when {
                    rating in 0.0..1.0 -> "very good"
                    rating in 1.1..2.0 -> "Normal"
                    rating in 2.1..3.0 -> "Awesome"
                    else -> ""
                }
                holder.status.text = ratingText
            }
        })

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, HeroDetailActivity::class.java).apply {
                putExtra("HERO_NAME", hero.title)
                putExtra("HERO_IMAGE", hero.image)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = heroesList.size

    public fun updateHeroList(newHeroList: List<Hero>) {
        heroesList = newHeroList
        notifyDataSetChanged()
    }

}
