package com.example.a491

// Menu Imports
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    // Category Menu
    lateinit var categoryMenuLayout: DrawerLayout
    lateinit var categoryMenuDrawerToggle: ActionBarDrawerToggle
    lateinit var categoryView: NavigationView
    val items = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Implement Fragments
        /*
        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_screen, ItemFragment(), null).commit()*/

        startActivity(Intent(this, LoginActivity::class.java))

        val recycler = findViewById<RecyclerView>(R.id.recycleView)
        recycler.layoutManager = GridLayoutManager(this, 2)

        val itemAdapter = ItemRecyclerViewAdapter(items, this, true, false)
        recycler.adapter = itemAdapter

        val fetcher = ItemFetcher(items, itemAdapter)
        fetcher.getItems()



        /*
        * Category Menu
        */

        // Category Menu instance of menu icon/toggle and menu layout
        categoryMenuLayout = findViewById(R.id.category_menu_layout)
        categoryMenuDrawerToggle = ActionBarDrawerToggle(this, categoryMenuLayout, R.string.open_category_menu, R.string.close_category_menu)

        // Add listener to toggle the menu
        categoryMenuLayout.addDrawerListener(categoryMenuDrawerToggle)
        categoryMenuDrawerToggle.syncState()

        // Makes the three bar/hamburger menu icon appear
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // For item selection from the category menu
        categoryView = findViewById(R.id.category_view)
        categoryView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) { // The 'when' expression is similar to switch-case expressions
                R.id.category1 -> {
                    Toast.makeText(this, "Category 1 clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.category2 -> {
                    Toast.makeText(this, "Category 2 clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.category3 -> {
                    Toast.makeText(this, "Category 3 clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }.also {
                // Close the drawer after item click
                categoryMenuLayout.closeDrawer(categoryView)
            }
        }

        /*
        * Bottom Navigation Bar
        */
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.itemIconTintList = null
        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeButton -> {
                    // do nothing
                }
                R.id.postButton -> {
//                    Toast.makeText(this, "Post Pressed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, RentFormActivity::class.java)
                    startActivity(intent)
                }
                R.id.profileButton -> {
                    //Toast.makeText(this, "Profile Pressed", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, ProfileActivity::class.java))
                }
            }
            navBar.itemIconTintList = null

            true
        }
    }

    // Function to open/close category menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (categoryMenuDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}