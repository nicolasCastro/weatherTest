package com.ncastro.weatherastrpay.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ncastro.weatherastrpay.databinding.WeatherItemBinding
import com.ncastro.weatherastrpay.model.WeatherResult
import com.ncastro.weatherastrpay.viewmodel.ItemClickListener

class WeatherViewHolder(private val binding: WeatherItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val viewModel = WeatherViewModel()

    fun bind(item: WeatherResult, listener: ItemClickListener) {
        viewModel.bind(itemView.context, item)
        binding.viewModel = viewModel
        binding.root.setOnClickListener {
            listener.onClick(item)
        }
    }
}