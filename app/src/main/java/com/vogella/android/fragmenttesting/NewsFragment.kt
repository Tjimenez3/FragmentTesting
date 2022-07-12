package com.vogella.android.fragmenttesting

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vogella.android.fragmenttesting.databinding.FragmentSecondBinding
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.vogella.android.fragmenttesting.adapter.INewsItemClickListener
import com.vogella.android.fragmenttesting.adapter.NewsAdapter
import com.vogella.android.fragmenttesting.constants.AnalyticsConstant.ANALYTICS_NEWS_OUTPUT
import com.vogella.android.fragmenttesting.constants.PreferenceConstant.*
import com.vogella.android.fragmenttesting.repository.NewsRepositoryImpl
import com.vogella.android.fragmenttesting.entity.NewsItem
import com.vogella.android.fragmenttesting.extension.changeLocalization
import com.vogella.android.fragmenttesting.uttilities.SharedPreferenceUtilities
import com.vogella.android.fragmenttesting.viewModel.NewsViewModel
import timber.log.Timber
import java.util.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewsFragment : BaseFragment(),INewsItemClickListener{

    private var _binding: FragmentSecondBinding? = null
    private val binding get()  = _binding!!
    private val mNewsAdapter = NewsAdapter()
    private lateinit var progressBar: ProgressBar
    private var newsViewModel = NewsViewModel(NewsRepositoryImpl(App.getApi(),App.getDatabase()))
    private lateinit var languageResources : Resources
    private var sharedPreferences = SharedPreferenceUtilities(App.getPreferences())



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragBinding: ViewDataBinding? = DataBindingUtil.inflate(inflater,R.layout.fragment_second,container, false)


        fragBinding?.lifecycleOwner = this

        _binding = FragmentSecondBinding.inflate(inflater, container, false)


        languageResources =  resources

        progressBar = binding.progressBar
        with(binding.recyclerview) {
            layoutManager = LinearLayoutManager(context)
            adapter = mNewsAdapter
        }
        binding.buttonRetry.setOnClickListener {
            newsViewModel.makeApiRequest()
        }
        mNewsAdapter.setOnItemClickListener(this)

        FirebaseApp.initializeApp(requireContext())
        FirebaseAnalytics.getInstance(requireContext()).logEvent(ANALYTICS_NEWS_OUTPUT,null)

        setToolbar()
        newsViewModel.makeApiRequest()

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
            toolbar.title = languageResources.getString(R.string.second_fragment_label)
            toolbar.menu.findItem(R.id.menu_french).title = languageResources.getString(R.string.french_title)
            toolbar.menu.findItem(R.id.menu_english).title = languageResources.getString(R.string.english_title)
            true

        }
        toolbar.title = languageResources.getString(R.string.second_fragment_label)

    }

    override fun onResume() {
        super.onResume()
        Timber.e("On Resume  Fragment 2")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.isError.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                languageResources.getString(R.string.retrieval_error),
                Toast.LENGTH_SHORT
            ).show()
        }

        newsViewModel.isProgressBarShown.observe(viewLifecycleOwner) {
            if (it == true) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        newsViewModel.responseNewsItems.observe(viewLifecycleOwner) {
            mNewsAdapter.setData(it)
            binding.buttonRetry.visibility = View.GONE
            binding.errorMessage.visibility = View.GONE

        }
        newsViewModel.isNewsListEmpty.observe(viewLifecycleOwner) {
            binding.buttonRetry.visibility = View.VISIBLE
            binding.errorMessage.visibility = View.VISIBLE
            binding.errorMessage.text = languageResources.getString(R.string.retrieval_error)
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onTitleClick(title: String?) {
//        Do nothing
    }

    override fun onImageClick(url: String?) {
        if (url != null) {
            sharedPreferences.setPreferencesString(PREFERENCE_CONSTANT_STRING,url.toString())
        }
        sharedPreferences.setPreferencesFinished()
        val action = ContainerFragmentDirections.actionFifthFragmentToThirdFragment()
        findNavController().navigate(action)
    }

    override fun onItemClick(obj: NewsItem) {
        sharedPreferences.setPreferenceNewsItem(PREFERENCE_CONSTANT_NEWS_ITEM,obj)
        sharedPreferences.setPreferencesFinished()
        val action = ContainerFragmentDirections.actionFifthFragmentToFourthFragment()
        findNavController().navigate(action)
    }


}