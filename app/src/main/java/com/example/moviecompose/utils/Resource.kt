package com.example.moviecompose.utils

import java.lang.Exception

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>(){
        fun getSuccessResult(): R{
            return result
        }
    }
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}