package com.example.fairmoneytechinicaltestapp.util


data class UIStatus<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): UIStatus<T> {
            return UIStatus(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): UIStatus<T> {
            return UIStatus(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): UIStatus<T> {
            return UIStatus(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}