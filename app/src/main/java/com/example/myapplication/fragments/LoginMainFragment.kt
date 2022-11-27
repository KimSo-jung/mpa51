package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginMainBinding
import com.example.myapplication.getMyReference
import com.example.myapplication.viewmodels.LoginMainViewModel

class LoginMainFragment : Fragment() {
    private var bind:FragmentLoginMainBinding?=null
    private val binding get() = bind!!
    private val viewModel: LoginMainViewModel by activityViewModels()

    companion object{
        private const val Firestore = "FIRESTORE"
        fun newInstance() = LoginMainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentLoginMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginStartbyEmailButton.setOnclickListener{
            view?.findNavController()?.navigate(R.id.action_loginMainFragment_to_emailLoginFragment)
        }
    }
    private val defaultOnAuthCompleteListener = object : OnAuthCompleteListener{
        override fun onNewUser(){
            super.onNewUser()
        }
        override fun onSuccess() {
            super.onSuccess()
            getMyReference()?.get()?.addOnCompleteListener {
                if (it.result.exists()) {
                    activity?.finish()
                } else {
                    requireActivity().findNavController(android.R.id.content)
                        .navigate(R.id.action_emailLoginFragment_to_setNickNameFragment)
                }
            }
        }

    }

}