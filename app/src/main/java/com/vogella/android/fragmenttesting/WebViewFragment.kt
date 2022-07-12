package com.vogella.android.fragmenttesting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vogella.android.fragmenttesting.constants.PreferenceConstant
import com.vogella.android.fragmenttesting.constants.PreferenceConstant.PREFERENCE_DEFAULT_STRING
import com.vogella.android.fragmenttesting.constants.UrlConstant
import com.vogella.android.fragmenttesting.constants.UrlConstant.DEFAULT_URL
import com.vogella.android.fragmenttesting.databinding.FragmentWebViewBinding
import com.vogella.android.fragmenttesting.extension.changeLocalization
import com.vogella.android.fragmenttesting.uttilities.SharedPreferenceUtilities
import java.util.*

class WebViewFragment : BaseFragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get()  = _binding!!
    private var sharedPreferences = SharedPreferenceUtilities(App.getPreferences())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebViewBinding.inflate(inflater,container,false)


        val stringUrl = sharedPreferences.getPreferencesString(PreferenceConstant.PREFERENCE_CONSTANT_STRING)
        with(binding.webView) {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true;
            settings.setSupportZoom(true)
            if (stringUrl != null) {
                loadUrl(stringUrl.toString())
                println(stringUrl.toString())
            }
            else {
                loadUrl(DEFAULT_URL)
            }

        }
        setToolbar()

        return binding.root
    }

    private fun setToolbar() {
        setHasOptionsMenu(true)

        val toolbar = binding.mytoolbar.root
        toolbar.inflateMenu(R.menu.menu_main)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_french) {
                val locale = Locale.FRENCH
                context.changeLocalization(locale)
            }
            else if (it.itemId == R.id.menu_english) {
                val locale = Locale.ENGLISH
                context.changeLocalization(locale)
            }
            toolbar.title = resources.getString(R.string.third_fragment_label)
            toolbar.menu.findItem(R.id.menu_french).title = resources.getString(R.string.french_title)
            toolbar.menu.findItem(R.id.menu_english).title = resources.getString(R.string.english_title)
            true

        }
        toolbar.title = resources.getString(R.string.third_fragment_label)

    }

}