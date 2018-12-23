package com.github.jmlb23.movielense.domain

import java.util.*

enum class Gender{
    MALE, FEMALE, OTHER;

    override fun toString(): String = when (this) {
        MALE -> "M"
        FEMALE -> "F"
        else -> "O"
    }
}

fun Char.toGender() =
        when (this) {
                'F' -> Gender.FEMALE
                'M' -> Gender.MALE
                else -> Gender.OTHER
        }


data class User(val id: Long, val age: Int, val gender: Gender, val occupationId: Long, val zipCode: String)
data class InputUser(val age: Int, val gender: Char, val occupationId: Long, val zipCode: String){
    fun toUser(): User = User(0,age,gender.toGender(),occupationId,zipCode)
}

data class Genre(val id: Int, val name: String)

data class Movie(val id: Long, val name: String, val dateOfPublish: Date)

data class Occupation(val id: Long, val name: String)

data class Rating(val id: Long, val userId: Long, val movieId: Long, val rating: Int, val ratedAt: Date){
    constructor(userId: Long, movieId: Long, rating: Int, ratedAt: Date) : this(0,userId,movieId,rating,ratedAt)
}