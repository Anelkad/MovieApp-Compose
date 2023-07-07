package com.example.moviecompose.movieDetails.ui.modelUI


sealed interface MovieDetailsEffect {
    object ShowWaitDialog: MovieDetailsEffect
    data class ShowToast(
        var text: String
        ): MovieDetailsEffect

    object NavigateBack: MovieDetailsEffect
}