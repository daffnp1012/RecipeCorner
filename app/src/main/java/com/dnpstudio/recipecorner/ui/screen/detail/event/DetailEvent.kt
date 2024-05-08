package com.dnpstudio.recipecorner.ui.screen.detail.event

import com.dnpstudio.recipecorner.ui.screen.home.event.HomeEvent

sealed class DetailEvent {

    data object getDetail: DetailEvent()

}