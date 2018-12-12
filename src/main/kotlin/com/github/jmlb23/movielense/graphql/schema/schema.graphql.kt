package com.github.jmlb23.movielense.graphql.schema

import com.github.jmlb23.movielense.datasources.exposed.Users
import com.github.jmlb23.movielense.domain.Gender
import com.github.jmlb23.movielense.domain.Rating
import com.github.jmlb23.movielense.domain.User
import com.github.jmlb23.movielense.repositories.RatingRepository
import com.github.jmlb23.movielense.repositories.UserRepository
import com.github.pgutkowski.kgraphql.KGraphQL
import org.joda.time.DateTime

val schema = KGraphQL.schema{
    query("allUsers"){
        resolver{ ->
                UserRepository.getAll().toList()
        }
    }

    query("user"){
        resolver{ id: Int ->
            UserRepository.filter{Users.id eq id }.toList().first()
        }
    }

    mutation("rate"){
        resolver{ userId: Long, movieId: Long, rating: Byte ->
            RatingRepository.add(Rating(userId,movieId,rating,DateTime.now().toDate()))
        }
    }
    
    type<User>()
    enum<Gender>()
    type<Rating>()
}