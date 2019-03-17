package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.Ratings
import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.domain.Rating
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object RatingRepository : Repository<Rating>{
    private fun ResultRow.toRating() = Rating(this[Ratings.id].toLong(),this[Ratings.userId].toLong(),this[Ratings.movieId].toLong(),this[Ratings.rating],this[Ratings.ratedAt].toDate())

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Rating> = transactionEnviroment {
        Ratings.select(predicate).map{ it.toRating() }

    }.asSequence()

    override fun add(element: Rating): Long =
            transactionEnviroment { Ratings.insert {
                it[movieId]= element.movieId.toInt()
                it[userId] = element.userId.toInt()
                it[rating] = element.rating
                it[ratedAt] = DateTime(element.ratedAt)
            }.generatedKey!!.toLong()
        }


    override fun getAll(): Sequence<Rating> =
        transactionEnviroment {
            Ratings.selectAll()
                    .toList()
                    .map{ it.toRating() }
        }.asSequence()


    override fun remove(indexer: Long): Long =
        transactionEnviroment {
            Ratings.deleteWhere { Ratings.id eq indexer.toInt() }
        }.toLong()


    override fun replace(indexer: Long, element: Rating): Long =
        transactionEnviroment {
            Ratings.update({Ratings.id eq indexer.toInt()}) {
                it[movieId]= element.movieId.toInt()
                it[userId] = element.userId.toInt()
                it[rating] = element.rating
                it[ratedAt] = DateTime(element.ratedAt)
            }
        }.toLong()


    override fun getElement(indexer: Long): Rating =
        transactionEnviroment {
            Ratings
                    .select {Ratings.id eq indexer.toInt()}
                    .map{ it.toRating() }
                    .first()
        }


}