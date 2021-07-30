package com.epam.bet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.epam.bet.entities.User
import com.epam.bet.extensions.showToast
import com.epam.bet.interfaces.AuthInterface
import com.epam.bet.viewmodel.BetsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
class MainActivity : AppCompatActivity(), AuthInterface {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var sharedPreferences : SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = null

        sharedPreferences = this.getSharedPreferences("BetAppSettings", Context.MODE_PRIVATE)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        val value = sharedPreferences.getString("email", "none")
        if (value == null || value == "none") {
            graph.startDestination = R.id.authFragment
            bottomNavigationView.visibility = View.INVISIBLE
        } else {
            graph.startDestination = R.id.betFragment
        }
        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun signIn(name: String, email: String) {
        bottomNavigationView.visibility = View.VISIBLE
        lifecycleScope.launch {
            sharedPreferences.edit().putString("name", name).apply()
            sharedPreferences.edit().putString("email", email).apply()
        }
    }

    override fun logOut() {
        bottomNavigationView.visibility = View.INVISIBLE
        lifecycleScope.launch {
            sharedPreferences.edit().remove("name").apply()
            sharedPreferences.edit().remove("email").apply()
        }
    }

    override fun getUserData(): User {
        val name = sharedPreferences.getString("name", "none")
        val email = sharedPreferences.getString("email", "none")
        return User(name!!, email!!)
    }

}