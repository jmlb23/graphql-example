package com.github.jmlb23.movielense

import com.github.jmlb23.movielense.datasources.exposed.Users
import com.github.jmlb23.movielense.domain.*
import com.github.jmlb23.movielense.repositories.Repository

class MovieLensService(private val movieRepo: Repository<Movie>,
                       private val genreRepo: Repository<Genre>,
                       private val occupationRepo: Repository<Occupation>,
                       private val ratingRepo: Repository<Rating>,
                       private val userRepo: Repository<User>) {

    fun getAllUsers() = userRepo.getAll().toList()
    fun getUser(id: Int) = userRepo.filter{ Users.id eq id }.toList().firstOrNull()

    fun createUser(age: Int, gender: String, occupationId: Long, zipCode: String): User {
        val newUser = User(0, age, gender.first().toGender(), occupationId, zipCode)
        val newId = userRepo.add(newUser)
        return newUser.copy(id = newId)
    }

    fun deleteUser(id: Long) = userRepo.remove(id)

    fun updateUser(id: Long, age: Int, gender: String, occupationId: Long, zipCode: String): Long =
            userRepo.replace(id, User(id=0,age = age,gender = gender.first().toGender(),occupationId = occupationId, zipCode = zipCode))
}