package com.example.androidtest.ex2.fragment

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtest.databinding.FragmentRecentlyAppBinding
import com.example.androidtest.ex2.adapter.AppAdapter

class RecentlyAppFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = RecentlyAppFragment()
    }

    private lateinit var binding: FragmentRecentlyAppBinding
    private val recentAppsList = mutableListOf<ApplicationInfo>()
    private lateinit var recentAppsAdapter: AppAdapter
    private val recentAppsSet = mutableSetOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecentlyAppBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadRecentApp()
    }

    override fun onResume() {
        super.onResume()
        if (checkUsageStatsPermission() && recentAppsList.isEmpty()) {
            loadRecentApp()
        }
    }

    private fun initView() {
        recentAppsAdapter = AppAdapter(requireContext(), recentAppsList)
        binding.rlRecentlyApp.adapter = recentAppsAdapter
        if (checkUsageStatsPermission()) {
            loadRecentApp()
        } else {
            requestUsageStatsPermission()
        }
    }

    @SuppressLint("ObsoleteSdkInt", "NotifyDataSetChanged")
    private fun loadRecentApp() {
        recentAppsList.clear()
        recentAppsSet.clear()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val usageStatsManager =
                requireContext().getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val time = System.currentTimeMillis()
            val usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                time - 1000 * 60 * 60 * 24, time
            )
            if (!usageStatsList.isNullOrEmpty()) {
                usageStatsList.sortedByDescending { it.lastTimeUsed }
                for (usageState in usageStatsList) {
                    if (!recentAppsSet.contains(usageState.packageName)) {
                        try {
                            val appInfo = requireContext().packageManager.getApplicationInfo(
                                usageState.packageName,
                                0
                            )
                            if ((appInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                                recentAppsList.add(appInfo)
                                recentAppsSet.add(usageState.packageName)
                            }
                        } catch (e: PackageManager.NameNotFoundException) {
                            e.printStackTrace()
                        }
                    }
                }
                recentAppsAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun checkUsageStatsPermission(): Boolean {
        val appOps = requireContext().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            requireContext().packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestUsageStatsPermission() {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        startActivity(intent)
    }
}
