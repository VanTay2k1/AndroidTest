package com.example.androidtest.ex2.fragment

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtest.databinding.FragmentAllAppBinding
import com.example.androidtest.ex2.adapter.AppAdapter

class AllAppFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AllAppFragment()
    }

    private lateinit var binding: FragmentAllAppBinding
    private val allAppList = mutableListOf<ApplicationInfo>()
    private lateinit var allAppsAdapter: AppAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllAppBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadAllApps()
    }

    private fun initView() {
        allAppsAdapter = AppAdapter(requireContext(), allAppList)
        binding.rlAllApp.adapter = allAppsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadAllApps() {
        val packageManager = requireContext().packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        for (app in apps) {
            if (packageManager.getLaunchIntentForPackage(app.packageName) != null) {
                allAppList.add(app)
            }
        }
        allAppsAdapter.notifyDataSetChanged()
    }
}
