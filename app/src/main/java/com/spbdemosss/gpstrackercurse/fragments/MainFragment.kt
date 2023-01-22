package com.spbdemosss.gpstrackercurse.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.spbdemosss.gpstrackercurse.databinding.FragmentMainBinding
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsOsm()
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOSM()
    }

    private fun settingsOsm(){
        Configuration.getInstance().load(
            activity as AppCompatActivity,
            activity?.getSharedPreferences("osm_pref", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
    }

    private fun initOSM() {
        binding.map.controller.setZoom(16.0)
        //binding.map.controller.animateTo(GeoPoint(60.02545482317729, 30.24435733504501))
        val mLocProvide = GpsMyLocationProvider(activity)
        val mLocOverlay = MyLocationNewOverlay(mLocProvide, binding.map)
        mLocOverlay.enableMyLocation()
        mLocOverlay.enableFollowLocation()
        mLocOverlay.runOnFirstFix{
            binding.map.overlays.clear()
            binding.map.overlays.add(mLocOverlay)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}