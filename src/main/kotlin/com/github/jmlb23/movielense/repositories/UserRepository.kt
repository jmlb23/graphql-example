package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.Users
import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.domain.User
import com.github.jmlb23.movielense.domain.toGender
import org.jetbrains.exposed.sql.*

object UserRepository : Repository<User>{


    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<User> = transactionEnviroment {
        Users.select(predicate).map{ User(it[Users.id].toLong(),it[Users.age],it[Users.gender].toGender(),it[Users.occupationId].toLong(),it[Users.zipCode].toString())}

    }.asSequence()

    override fun add(element: User): Boolean =
            transactionEnviroment { Users.insert {
                it[age]= element.age.toInt()
                it[gender] = element.gender.let { it.toString().first() }
                it[occupationId] = element.occupationId.toInt()
                it[zipCode] = element.zipCode
            }.generatedKey!!
        }.toInt() > 0


    override fun getAll(): Sequence<User> =
        transactionEnviroment {
            Users.selectAll()
                    .toList()
                    .map{ User(it[Users.id].toLong(),it[Users.age],it[Users.gender].toGender(),it[Users.occupationId].toLong(),it[Users.zipCode].toString())}
        }.asSequence()


    override fun remove(indexer: Long): Long =
        transactionEnviroment {
            Users.deleteWhere { Users.id eq indexer.toInt() }
        }.toLong()


    override fun replace(indexer: Long, element: User): Long =
        transactionEnviroment {
            Users.update({Users.id eq indexer.toInt()}) {
                it[Users.age] = element.age.toInt()
                it[Users.gender] = element.gender.toString().first()
                it[Users.occupationId] = element.occupationId.toInt()
                it[Users.zipCode] = element.zipCode
            }
        }.toLong()


    override fun getElement(indexer: Long): User =
        transactionEnviroment {
            Users
                    .select {Users.id eq indexer.toInt()}
                    .map { x -> User(x[Users.id].toLong(),x[Users.age],x[Users.gender].toGender(),x[Users.occupationId].toLong(),x[Users.zipCode].toString()) }
                    .first()
        }


}