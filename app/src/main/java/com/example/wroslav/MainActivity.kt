package com.example.wroslav


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.wroslav.Adapters.EventAdapter
import com.example.wroslav.Adapters.NewsAdapter
import com.example.wroslav.Adapters.PlacesAdapter
import com.example.wroslav.dataClasses.Events
import com.example.wroslav.dataClasses.MyPlace
import com.example.wroslav.dataClasses.News
import com.example.wroslav.Fragments.FragmentEventsDirections
import com.example.wroslav.Fragments.FragmentNewsDirections
import com.example.wroslav.Fragments.FragmentPlacesDirections
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import java.util.*


class MainActivity : AppCompatActivity(), NewsAdapter.OnNewsSelected,
    PlacesAdapter.OnPlacesSelected, EventAdapter.OnEventsSelected,
    MultiplePermissionsListener {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        auth = FirebaseAuth.getInstance()
//        if(auth.currentUser == null){
////            val intent = Intent(this, LoginActivity::class.java)
////            startActivity(intent)
////            finish()
//            Toast.makeText(this, "NO logged", Toast.LENGTH_LONG).show()
//        }else{
//            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show()
//        }

        loadLocate() // load language
        setContentView(R.layout.activity_main)
        requestPermission()
//        val toolbar=findViewById<MaterialToolbar>(R.id.toolbarPlace)
//        setSupportActionBar(toolbar)
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_navigationFragment)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
            NavigationUI.setupWithNavController(bottomNav, navController)
    }


    private fun requestPermission() {
        Dexter.withActivity(this).withPermissions(
            Arrays.asList(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            )
        ).withListener(this).check()
    }


    override fun OnPlacesSelected(myPlace: MyPlace) {
        val action = FragmentPlacesDirections.actionFragmentPlacesToFragmentPlaceExtended(myPlace)
        findNavController(R.id.nav_navigationFragment).navigate(action)
    }

    override fun OnNewsSelected(news: News) {
        val action = FragmentNewsDirections.actionFragmentNewsToArticleFragment(news)
        findNavController(R.id.nav_navigationFragment).navigate(action)
    }
    override fun onEventsSelected(events: Events) {
        val action = FragmentEventsDirections.actionFragmentEventsToEventExtendedFragment(events)
        findNavController(R.id.nav_navigationFragment).navigate(action)
    }
    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

    }
    override fun onPermissionRationaleShouldBeShown(
        permissions: MutableList<PermissionRequest>?,
        token: PermissionToken?
    ) {
        Toast.makeText(this, "You must enable permission", Toast.LENGTH_LONG)
    }
    private fun setLocate(Lang: String) { // call this to change language String example "ru" hhhh

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en")
        if (language != null) {
            setLocate(language)
        }
    }
}

@BindingAdapter("android:setImage")
fun setImage(imageView: ImageView, imageUrl:String){
    Picasso.get().load(imageUrl).into(imageView)
}


