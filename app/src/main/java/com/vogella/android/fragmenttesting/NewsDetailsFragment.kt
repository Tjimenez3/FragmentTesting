package com.vogella.android.fragmenttesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vogella.android.fragmenttesting.constants.PreferenceConstant.PREFERENCE_CONSTANT_NEWS_ITEM
import com.vogella.android.fragmenttesting.constants.UrlConstant
import com.vogella.android.fragmenttesting.constants.UrlConstant.DEFAULT_URL
import com.vogella.android.fragmenttesting.constants.loadImage
import com.vogella.android.fragmenttesting.database.DatabaseService
import com.vogella.android.fragmenttesting.database.NewsItemDatabase
import com.vogella.android.fragmenttesting.databinding.FragmentNewsDetailsBinding
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.entity.NewsModel
import com.vogella.android.fragmenttesting.extension.changeLocalization
import com.vogella.android.fragmenttesting.uttilities.DateParsingUtils
import com.vogella.android.fragmenttesting.uttilities.SharedPreferenceUtilities
import java.util.*

class NewsDetailsFragment: DialogFragment(){

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get()  = _binding!!
    private val utils = DateParsingUtils()
    private val sharedPreferences = SharedPreferenceUtilities(App.getPreferences())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsDetailsBinding.inflate(inflater,container,false)
        setToolbar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val args : NewsDetailsFragmentArgs by navArgs()
//        val newsItem = args.arg1
//        addDetails(newsItem)

        var test = sharedPreferences
        val newsItem = sharedPreferences.getPreferenceNewsItem(PREFERENCE_CONSTANT_NEWS_ITEM)
        addDetails(newsItem)

        super.onViewCreated(view, savedInstanceState)
    }


    private fun addDetails(item: NewsItem) {
        binding.title.text = item.title
        binding.author.text = item.author
        binding.content.text = item.content
        binding.description.text = item.description
        binding.publishedAt.text =
            item.publishedAt?.let { utils.parse(it, utils.getinputFormat(), utils.getOutputFormat()) }
        binding.image.loadImage(item.urlToImage)
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
            toolbar.title = resources.getString(R.string.fourth_fragment_label)
            toolbar.menu.findItem(R.id.menu_french).title = resources.getString(R.string.french_title)
            toolbar.menu.findItem(R.id.menu_english).title = resources.getString(R.string.english_title)
            true

        }
        toolbar.title = resources.getString(R.string.fourth_fragment_label)

    }
}
