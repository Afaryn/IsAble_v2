package com.example.isable_capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.isable_capstone.databinding.ActivityMainBinding
import com.example.isable_capstone.ui.home.HomeFragment
import com.example.isable_capstone.ui.profile.ProfileFragment
import com.example.isable_capstone.ui.translate.CameraActivity
import com.example.isable_capstone.ui.translate.CameraActivity.Companion.CAMERAX_RESULT

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_profile -> replaceFragment(ProfileFragment())
            }
            true

        }
    }

    private fun setupView() {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
        supportActionBar?.show()
        supportActionBar?.title = "Home Page"
        binding.fab.setOnClickListener { navigateToCamera() }

    }

    private fun navigateToCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERAX_RESULT) {
//            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
//            showImage()
//        }
//    }

    fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}