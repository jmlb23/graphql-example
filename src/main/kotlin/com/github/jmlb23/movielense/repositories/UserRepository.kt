package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.Users
import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.domain.User
import com.github.jmlb23.movielense.domain.toGender
import org.jetbrains.exposed.sql.*

object UserRepository : Repository<User>{
    private fun ResultRow.toUser() = User(this[Users.id].toLong(),this[Users.age],this[Users.gender].toGender(),this[Users.occupationId].toLong(),this[Users.zipCode].toString())

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<User> = transactionEnviroment {
        Users.select(predicate).map{ it.toUser() }

    }.asSequence()

    override fun add(element: User): Long =
            transactionEnviroment { Users.insert {
                it[age]= element.age
                it[gender] = element.gender.toString().first()
                it[occupationId] = element.occupationId.toInt()
                it[zipCode] = element.zipCode
            }.generatedKey!!.toLong()
        }


    override fun getAll(): Sequence<User> =
        transactionEnviroment {
            Users.selectAll()
                    .toList()
                    .map{ it.toUser() }
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
                    .map { it.toUser() }
                    .first()
        }


}