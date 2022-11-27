package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFriendBinding
import com.example.myapplication.viewmodels.FriendViewModel
import com.google.firebase.firestore.ListenerRegistration

//친구 목록 프래그먼트
class FriendFragment : Fragment(R.layout.fragment_friend) {
    var snapshotListener: ListenerRegistration?=null
    private var bind : FragmentFriendBinding?=null
    val binding get() = bind!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentFriendBinding.inflate(inflater,container,false)
        return bind!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val friendModel: FriendViewModel by viewModels()

        //binding.recycler

    }


}