package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityMainBinding
    private lateinit var appbarc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navhf = supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment
        val navController = navhf.navController
        binding.bottomNavigationView.setupwithNavcontroller(navController)
        appbarc = AppBarConfiguration(
            setOf(
                R.id.profileFragment,
                R.id.
                R.id.postFragment
            )
        )

        auth = Firebase.auth
        firestore = Firebase.firestore
        storage = Firebase.storage

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null)
            startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navigation_host)
        return navController.navigateUp(appbarc) || super.onSupportNavigateUp()
    }

}