package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cacatrackermobileapp.data.models.UserSession

class MainUserViewModel: ViewModel() {

fun check(){
    Log.d("SCREEN", "MainUserPage")
}


    fun logout() {
        UserSession.clearUserInfo()
    }

}