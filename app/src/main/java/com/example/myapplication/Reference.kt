package com.example.myapplication

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

fun getImageRef(fileName: String):StorageReference?{
    val storage = Firebase.storage
    val storageRef = storage.reference
    if(fileName == "") return null
    return storage.getReferenceFromUrl(fileName)
}

fun getUserDoc(uid: String):DocumentReference?{
    val firestore = Firebase.firestore
    if(uid.isEmpty()) return null
    val userCollection = firestore.collection("Users")
    return userCollection.document(uid)
}

fun nicknameUsable(nickname: String):Boolean{
    val firestore = Firebase.firestore
    if(nickname.isEmpty()) return false
    val userCollection = firestore.collection("Users")
    val query = userCollection.whereEqualTo("nickname", nickname)
    return query.get().result.isEmpty
}

fun UserCollection():CollectionReference{
    val firestore = Firebase.firestore
    return firestore.collection("Users")
}

fun getMyReference():DocumentReference?{
    val auth = Firebase.auth
    if(auth.currentUser!=null)
        return auth.uid?.let { getUserDoc(it) }
    return null
}

