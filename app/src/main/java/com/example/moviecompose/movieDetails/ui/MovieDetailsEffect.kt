package com.example.moviecompose.movieDetails.ui


sealed interface MovieDetailsEffect {
    object ShowWaitDialog: MovieDetailsEffect
    data class ShowToast(
        var text: String
        ): MovieDetailsEffect

    object NavigateBack: MovieDetailsEffect
}