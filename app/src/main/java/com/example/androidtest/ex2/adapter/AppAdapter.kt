package com.example.androidtest.ex2.adapter

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest.databinding.ItemAppBinding

class AppAdapter(private val context: Context, private val appList: List<ApplicationInfo>) :
    RecyclerView.Adapter<AppAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemAppBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = appList.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(appList[position], context.packageManager)
    }


    class ItemHolder(private val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(applicationInfo: ApplicationInfo, packageManager: PackageManager) {
            binding.run {
                imgAppIcon.setImageDrawable(applicationInfo.loadIcon(packageManager))
                tvNameApp.text = applicationInfo.loadLabel(packageManager)
            }
        }
    }
}
