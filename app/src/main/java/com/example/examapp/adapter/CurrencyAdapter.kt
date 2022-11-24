package com.example.examapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.examapp.R
import com.example.examapp.activity.MainActivity
import com.example.examapp.database.entity.Currency
import com.example.examapp.databinding.CurrencyListItemBinding
import com.example.examapp.fragments.CurrencyDetailsFragment

class CurrencyAdapter(private val currencies: List<Currency>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(val binding: CurrencyListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CurrencyListItemBinding.inflate(layoutInflater, parent, false)

        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentCurrency = currencies[position]
        holder.binding.apply {
            this.currency = currentCurrency

            ivLiked.visibility = if (currentCurrency.favourite) View.VISIBLE else View.GONE

            tvPrice.text = holder.binding.root.context
                .getString(R.string.price, currentCurrency.currentPrice)
            
            Glide
                .with(root.context)
                .load(currentCurrency.logo)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivLogo)

            holder.binding.root.setOnClickListener {
                (it.context as MainActivity).supportFragmentManager.commit {
                    val bundle = Bundle()
                    bundle.putString("id", currentCurrency.id)
                    replace(R.id.fragment_view, CurrencyDetailsFragment::class.java, bundle)
                    addToBackStack("countries_list_to_details")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }
}


