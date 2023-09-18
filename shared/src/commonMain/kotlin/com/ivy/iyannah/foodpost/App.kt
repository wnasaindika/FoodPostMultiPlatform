package com.ivy.iyannah.foodpost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ivy.iyannah.foodpost.store.createStore
import com.ivy.iyannah.foodpost.ui.FoodPostPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        FoodPostPage(modifier = Modifier.fillMaxSize(), onBackPres = {}, onSearchPres = {}) {
        }
    }
}


