package com.example.cacatrackermobileapp.ui.mainuser

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.MainUserViewModel

@Composable
fun MainUserScreen(
    viewModel: MainUserViewModel = viewModel()
){

}

@Preview(showBackground = true)
@Composable
fun PreviewMainUserScreen(){
    CacaTrackerMobileAppTheme {
        MainUserScreen()
    }
}
