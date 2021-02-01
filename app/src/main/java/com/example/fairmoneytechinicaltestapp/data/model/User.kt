package com.example.fairmoneytechinicaltestapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var id: String,
    var title: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var gender: String? = null,
    var email: String? = null,
    val phone: String? = null,
    var dateOfBirth: String? = null,
    var registerDate: String? = null,
    var picture: String? = null,
)