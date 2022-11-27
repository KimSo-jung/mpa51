package com.example.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.myapplication.DTO.Users

class UserList {
    private val Flist = MutableLiveData<List<Users>>()
    fun observe(owner: LifecycleOwner,observer: Observer<List<Users>>){
        Flist.observe(owner,observer)
    }

    init {
        Flist.value = listOf()
    }

    fun setLists(list: List<Users>){
        this.Flist.value = list
    }

    fun getItems(index:Int):Users{
        return (
                if(index>Flist.value!!.size)
                    Users(uid = Users.INVALID_USER,"","")
                else
                    Flist.value!![index]
                )
    }

    fun getSize():Int = Flist.value!!.size
}
