package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.Movies
import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.domain.Movie
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object MovieRepository : Repository<Movie>{
    private fun ResultRow.toMovie()  = Movie(this[Movies.id].toLong(),this[Movies.title],this[Movies.releaseDate].toDate())

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Movie> = transactionEnviroment {
        Movies.select(predicate).map{ it.toMovie() }

    }.asSequence()

    override fun add(element: Movie): Long =
            transactionEnviroment { Movies.insert {
                it[title] = element.name
                it[releaseDate] = DateTime(element.dateOfPublish)
            }.generatedKey!!.toLong()
            }


    override fun getAll(): Sequence<Movie> =
            transactionEnviroment {
                Movies.selectAll()
                        .toList()
                        .map{ it.toMovie() }
            }.asSequence()


    override fun remove(indexer: Long): Long =
            transactionEnviroment {
                Movies.deleteWhere { Movies.id eq indexer.toInt() }
            }.toLong()


    override fun replace(indexer: Long, element: Movie): Long =
            transactionEnviroment {
                Movies.update({Movies.id eq indexer.toInt()}) {
                    it[Movies.title] = element.name
                    it[Movies.releaseDate] = DateTime(element.dateOfPublish)
                }
            }.toLong()


    override fun getElement(indexer: Long): Movie =
            transactionEnviroment {
                Movies
                        .select {Movies.id eq indexer.toInt()}
                        .map{ it.toMovie() }
                        .first()
            }
}