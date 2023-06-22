package com.example.moviecompose.movieList.ui

interface EventHandler<T> {
    fun obtainEvent(event: T)
}