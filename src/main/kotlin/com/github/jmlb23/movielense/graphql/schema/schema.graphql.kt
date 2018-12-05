package com.github.jmlb23.movielense.graphql.schema

import com.github.jmlb23.movielense.datasources.exposed.Users
import com.github.jmlb23.movielense.domain.Gender
import com.github.jmlb23.movielense.domain.User
import com.github.jmlb23.movielense.repositories.UserRepository
import com.github.pgutkowski.kgraphql.KGraphQL

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
    type<User>()
    enum<Gender> {  }
}