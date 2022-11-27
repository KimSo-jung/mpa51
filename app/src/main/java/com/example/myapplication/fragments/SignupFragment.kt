package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignupBinding
import com.example.myapplication.getMyReference
import com.example.myapplication.viewmodels.LoginMainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignupFragment : Fragment() {
    private val loginViewModel:LoginMainViewModel by viewModels()
    private var bind:FragmentSignupBinding?=null
    private val binding get() = bind!!
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentSignupBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val signupButton = binding.signUp


        val afterTextChangeListener = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        usernameEditText.addTextChangedListener(afterTextChangeListener)
        passwordEditText.addTextChangedListener(afterTextChangeListener)
        loginViewModel.loginFormState.observe(viewLifecycleOwner,
        Observer { loginFormState ->
            signupButton.isEnabled=loginFormState.DataValidation
            loginFormState.usernameError?.let {
                usernameEditText.error = getString(it)
            }
            loginFormState.passwordError?.let {
                passwordEditText.error = getString(it)
            }
        })

        signupButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    val doc = getMyReference()?.get()?.isSuccessful
                    if(doc==false)
                        view?.findNavController()
                            ?.navigate(R.id.action_emailLoginFragment_to_setNickNameFragment)
                }else{
                    val activity = activity
                    if (activity!=null)
                        Snackbar.make(
                            activity.findViewById(android.R.id.content),
                            "Failed to sign in${it.exception.toString()}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }
}