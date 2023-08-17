package com.example.warriorsofhind.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.warriorsofhind.models.Wallpaper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

enum class STATUS {
    LOADING,
    SUCCESS,
    ERROR
}
const val TAG = "FIREBASE"


class FirebaseViewModel : ViewModel() {
    private val _wallapapers = MutableLiveData<List<Wallpaper>>()
    val wallpaper: LiveData<List<Wallpaper>> = _wallapapers

    private val _status = MutableLiveData<STATUS>()
    val status: LiveData<STATUS> = _status

    val db = Firebase.firestore

    init {
        loadWallpaper()
    }

    fun loadWallpaper() {
        _status.postValue(STATUS.LOADING)
        try {
            val docRef = db.collection("wallpaper").get()
                .addOnSuccessListener {
                    if (!it.isEmpty) {

                        val wallpaperList = it.documents.map { doc ->
                            val name = doc.getString("name") ?: ""
                            val img = doc.getString("img") ?: ""
                            Wallpaper(name = name, img = img)
                        }
                        _wallapapers.postValue(wallpaperList)
                        _status.postValue(STATUS.SUCCESS)

                    }
                }
                .addOnFailureListener {
                    _status.postValue(STATUS.ERROR)
                    _wallapapers.postValue(emptyList())
                    Log.d(TAG, "get failed with ", it)
                }
        } catch (e: Exception) {
            _status.postValue(STATUS.ERROR)
            _wallapapers.postValue(emptyList())
            Log.d(TAG, "get failed with ", e)
        }
    }
}