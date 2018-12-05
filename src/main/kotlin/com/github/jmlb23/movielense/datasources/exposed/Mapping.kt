package com.github.jmlb23.movielense.datasources.exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Movies : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val title = varchar("title",255)
    val releaseDate = date("release_date")
}

object Users : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val age = integer("age")
    val gender = char("gender")
    val occupationId = integer("occupation_id").references(Occupations.id)
    val zipCode = varchar("zip_code",255)
}

object Occupations : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",255)
}

object Ratings : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val userId = integer("user_id").references(Users.id)
    val movieId = integer("movie_id").references(Movies.id)
    val rating = integer("rating")
    val ratedAt = date("rated_at")
}

object Genres : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name",255)
}

object GenresMovies : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val movieId = integer("movie_id").references(Movies.id)
    val genreId = integer("genre_id").references(Genres.id)
}

fun <T> transactionEnviroment(closure: () -> T): T {
    Database.connect(url="jdbc:postgresql://localhost:5432/movielens",user="postgres",password = "def456..",driver = "org.postgresql.Driver")
    return transaction{ closure()}
}

