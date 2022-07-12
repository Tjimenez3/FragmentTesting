package com.vogella.android.fragmenttesting

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.vogella.android.fragmenttesting.constants.AnalyticsConstant.ANALYTICS_LOGIN_OUTPUT
import com.vogella.android.fragmenttesting.constants.UrlConstant.DEFAULT_URL
import com.vogella.android.fragmenttesting.databinding.FragmentFirstBinding
import com.vogella.android.fragmenttesting.extension.changeLocalization
import com.vogella.android.fragmenttesting.repository.NewsRepositoryImpl
import com.vogella.android.fragmenttesting.viewModel.AuthenticateViewModel
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : DialogFragment(){

    private lateinit var context: FragmentActivity
    private lateinit var emailEditTextView : EditText
    private lateinit var passwordEditTextView : EditText
    private var loginViewModel: AuthenticateViewModel = AuthenticateViewModel(NewsRepositoryImpl(App.getFakeApi(),App.getDatabase()))
    private var _binding: FragmentFirstBinding? = null
    private val binding get()  = _binding!!
    private lateinit var languageResources : Resources
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater,container,savedInstanceState);
        
        context = activity as FragmentActivity
        _binding = FragmentFirstBinding.inflate(inflater,container,false)
        languageResources =  resources

        setToolbar()
        FirebaseApp.initializeApp(requireContext());
        FirebaseAnalytics.getInstance(requireContext()).logEvent(ANALYTICS_LOGIN_OUTPUT,null)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var emailEditText= ""
        var passwordEditText: String

        binding.buttonFirst.setOnClickListener {
            emailEditTextView = binding.emailEditText
            passwordEditTextView = binding.passwordEditText

            emailEditTextView.selectAll()
            passwordEditTextView.selectAll()

            emailEditText = emailEditTextView.text.toString()
            passwordEditText = passwordEditTextView.text.toString()

            loginViewModel.login(emailEditText,passwordEditText)
        }
        loginViewModel.isPasswordIncorrect.observe(viewLifecycleOwner, Observer {
            passwordEditTextView.error = languageResources.getString(R.string.password_error)
        })

        loginViewModel.isEmailIdIncorrect.observe(viewLifecycleOwner, Observer {
            emailEditTextView.error = languageResources.getString(R.string.email_error)
        })
        loginViewModel.isSessionActive.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), languageResources.getString(R.string.session_error), Toast.LENGTH_SHORT).show();
        })
        loginViewModel.isError.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), languageResources.getString(R.string.retrieval_error), Toast.LENGTH_SHORT).show();
        })

        loginViewModel.isProgressBarShown.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            }
            else {
                binding.progressBar.visibility = View.GONE
            }
        })

        loginViewModel.isAuthenticated.observe(viewLifecycleOwner, Observer {

            val action = LoginFragmentDirections.actionFirstFragmentToFifthFragment()
            findNavController().navigate(action)


        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setToolbar() {
        setHasOptionsMenu(true)

        val toolbar = binding.mytoolbar.root
        toolbar.inflateMenu(R.menu.menu_main)

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_french) {
                val locale = Locale.FRENCH
                context.changeLocalization(locale)
                updateLanguage()
            }
            else if (it.itemId == R.id.menu_english) {
                val locale = Locale.ENGLISH
                context.changeLocalization(locale)
                updateLanguage()
            }
            toolbar.title = languageResources.getString(R.string.first_fragment_label)
            toolbar.menu.findItem(R.id.menu_french).title = languageResources.getString(R.string.french_title)
            toolbar.menu.findItem(R.id.menu_english).title = languageResources.getString(R.string.english_title)
            true

        }
        toolbar.title = languageResources.getString(R.string.first_fragment_label)

    }
    private fun updateLanguage() {
        binding.mytoolbar.root.title = languageResources.getString(R.string.first_fragment_label)
        binding.emailEditText.hint = languageResources.getString(R.string.email_hint)
        binding.passwordEditText.hint = languageResources.getString(R.string.password_hint)
        binding.buttonFirst.text = languageResources.getString(R.string.submit_login)
        binding.mytoolbar.root.menu.findItem(R.id.menu_french).title = languageResources.getString(R.string.french_title)
        binding.mytoolbar.root.menu.findItem(R.id.menu_english).title = languageResources.getString(R.string.english_title)
    }


    

}