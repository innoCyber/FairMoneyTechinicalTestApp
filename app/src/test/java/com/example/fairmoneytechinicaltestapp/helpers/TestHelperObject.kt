package com.example.fairmoneytechinicaltestapp.helpers

import com.example.fairmoneytechinicaltestapp.data.model.User
import com.example.fairmoneytechinicaltestapp.data.model.UserResponse


object TestHelperObject {

    val user = User("innocent007","Mr","innocent","Ileka","male","innocentileka@gmail.com","24/3/1994","20/20/2033","innocent007.png")

    val userList =  listOf(
        User("innocent007","Mr","innocent","Ileka","male","innocentileka@gmail.com","24/3/1994","20/20/2033","innocent007.png")
    )

    val userResponse = UserResponse(
        listOf(
            User("innocent007","Mr","innocent","Ileka","male","innocentileka@gmail.com","24/3/1994","20/20/2033","innocent007.png")
        )
    )
}