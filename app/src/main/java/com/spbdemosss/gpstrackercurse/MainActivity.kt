package com.spbdemosss.gpstrackercurse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.spbdemosss.gpstrackercurse.databinding.ActivityMainBinding
import com.spbdemosss.gpstrackercurse.fragments.MainFragment
import com.spbdemosss.gpstrackercurse.fragments.SettingsFragment
import com.spbdemosss.gpstrackercurse.fragments.TrackFragment
import com.spbdemosss.gpstrackercurse.utils.openFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBottomNavClick()
        openFragment(MainFragment.newInstance())
    }

    private fun onBottomNavClick(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.id_home -> openFragment(MainFragment.newInstance())
                R.id.id_tracks -> openFragment(TrackFragment.newInstance())
                R.id.id_settings -> openFragment(SettingsFragment.newInstance())
            }
            true
        }
    }
}