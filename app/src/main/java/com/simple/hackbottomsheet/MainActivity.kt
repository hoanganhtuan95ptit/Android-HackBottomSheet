package com.simple.hackbottomsheet

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.simple.hackbottomsheet.databinding.ActivityMainBinding
import com.simple.hackbottomsheet.ui.Preview1BottomSheetDialogFragment
import com.simple.hackbottomsheet.ui.Preview2BottomSheetDialogFragment
import com.tuanhoang.deviceround.DeviceRound
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.findViewById<ViewGroup>(android.R.id.content).setBackgroundColor(Color.BLACK)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnOpenPreview1.setOnClickListener {

            Preview1BottomSheetDialogFragment().show(supportFragmentManager, "")
        }

        binding.btnOpenPreview2.setOnClickListener {

            Preview2BottomSheetDialogFragment().show(supportFragmentManager, "")
        }


        DeviceRound.init(this, getSharedPreferences("test", MODE_PRIVATE), null)

        lifecycleScope.launch {

            DeviceRound.fetchRoundAsync().collect {

                binding.rounded.setRadius(it ?: return@collect)
            }
        }

        binding.root.setBackgroundColor(Color.TRANSPARENT)
        (binding.root as? View)?.setBackgroundColor(Color.TRANSPARENT)
        window.decorView.setBackgroundColor(Color.TRANSPARENT)
        (window.decorView.parent as? View)?.setBackgroundColor(Color.TRANSPARENT)
    }
}