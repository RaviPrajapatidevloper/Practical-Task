package com.ravi.practicaltaskmvvm
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.ravi.practicaltaskmvvm.common.BaseActivity
import com.ravi.practicaltaskmvvm.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() =ActivityMainBinding::inflate

    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun setup() {
        setupNavGraph()
    }

    private fun setupNavGraph(){
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.nav_graph_main)
    }
}