package com.ncastro.weatherastrpay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ncastro.weatherastrpay.R
import com.ncastro.weatherastrpay.databinding.WeatherItemBinding
import com.ncastro.weatherastrpay.model.WeatherResult
import com.ncastro.weatherastrpay.viewmodel.ItemClickListener

class WeatherAdapter : RecyclerView.Adapter<WeatherViewHolder>() {

    private lateinit var items: List<WeatherResult>
    private lateinit var listener: ItemClickListener

    fun update(items: List<WeatherResult>, listener: ItemClickListener) {
        this.listener = listener
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding: WeatherItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.weather_item, parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return if (::items.isInitialized) items.size else 0
    }
}