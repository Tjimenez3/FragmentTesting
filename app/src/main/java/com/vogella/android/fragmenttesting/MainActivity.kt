package com.vogella.android.fragmenttesting

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import timber.log.Timber

class MainActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Timber.e("Hi")
        super.onCreateOptionsMenu(menu)
        return false
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.e("Hi")
        super.onOptionsItemSelected(item)
        return false
    }



}
