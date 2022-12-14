package com.example.myapplication.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentEmailLoginBinding
import com.example.myapplication.getMyReference
import com.example.myapplication.viewmodels.LoginMainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private val loginViewModel:LoginMainViewModel by viewModels()
    private var bind:FragmentEmailLoginBinding?=null
    private val binding get() = bind!!
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentEmailLoginBinding.inflate(inflater,container,false)
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
        val loginButton = binding.login


        val afterTextChangedListener = object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }

        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        loginViewModel.loginFormState.observe(viewLifecycleOwner,
        Observer { loginFormState->
            loginButton.isEnabled =loginFormState.DataValidation
            loginFormState.usernameError?.let {
                usernameEditText.error = getString(it)
            }
            loginFormState.passwordError?.let {
                passwordEditText.error = getString(it)
            }
        })
        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            ).addOnCompleteListener{
                if(it.isSuccessful){
                    getMyReference()?.get()?.addOnCompleteListener{
                        if (it.result.exists()){
                            activity?.finish()
                        }else{
                            view.findNavController()
                                .navigate(R.id.action_emailLoginFragment_to_setNickNameFragment)
                        }
                    }
                }else{
                    val activity = activity
                    if(activity!=null)
                        Snackbar.make(
                            activity.findViewById(android.R.id.content),
                            "failed to log in.${it.exception.toString()}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                }
            }
        }
        binding.signUp.setOnClickListener{
            view.findNavController()
                .navigate(R.id.action_emailLoginFragment_to_emailSignUpFragment)
        }
    }
}