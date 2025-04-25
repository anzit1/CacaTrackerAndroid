package com.example.cacatrackermobileapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.cacatrackermobileapp.data.models.UserSession

class MainUserViewModel: ViewModel() {
    fun logout() {
        UserSession.clearUserInfo()
    }

}