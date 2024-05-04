package com.dnpstudio.recipecorner.ui.screen.home.event

sealed class HomeEvent {
    data object getRecipe: HomeEvent()
    data object getDetail: HomeEvent()
}