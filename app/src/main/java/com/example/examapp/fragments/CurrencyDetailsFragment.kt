package com.example.examapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.examapp.R
import com.example.examapp.activity.MainActivity
import com.example.examapp.database.entity.asCurrencyDetails
import com.example.examapp.databinding.FragmentCurrencyDetailsBinding
import com.example.examapp.models.asCurrency
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedCurrency = arguments?.getString("id", null)
        lifecycleScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.getCurrencyById(
                selectedCurrency ?: return@launch
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyDetailsBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            (activity as? MainActivity)?.currencyViewModel?.selectCurrency?.collect {
                binding.currency = it?.asCurrencyDetails(binding.root.context) ?: return@collect

                (activity as? MainActivity)?.runOnUiThread {
                    if (it.priceChangePercentage < 0.0f) {
                        binding.tvPercentageMarketCap.setTextColor(
                            ContextCompat.getColor(context ?: return@runOnUiThread, R.color.red)
                        )
                    }

                    if (it.marketCapChangePercentage < 0.0f) {
                        binding.tvPercentagePrice.setTextColor(
                            ContextCompat.getColor(context ?: return@runOnUiThread, R.color.red)
                        )
                    }

                    setDataBinding()

                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.logo)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivLogo)
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.currency ?: return

        if (binding.currency?.favourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val currency = binding.currency

            currency?.favourite = currency?.favourite != true

            if (currency?.favourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            lifecycleScope.launch {
                (activity as? MainActivity)?.currencyViewModel?.setAsFavorite(
                    currency?.asCurrency() ?: return@launch
                )
            }
        }
    }
}