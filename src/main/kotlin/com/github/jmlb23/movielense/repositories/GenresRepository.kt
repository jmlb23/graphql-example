package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.datasources.exposed.Genres
import com.github.jmlb23.movielense.domain.Genre
import org.jetbrains.exposed.sql.*

object GenresRepository : Repository<Genre>{

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Genre> = transactionEnviroment {
        Genres.select(predicate).map{ Genre(it[Genres.id],it[Genres.name]) }

    }.asSequence()

    override fun add(element: Genre): Long =
            transactionEnviroment {
                Genres.insert {
                    it[name] = element.name
                }.generatedKey!!.toLong()
            }

    override fun getAll(): Sequence<Genre> = transactionEnviroment {
        Genres.selectAll()
                .toList()
                .map{ Genre(it[Genres.id],it[Genres.name])}
    }.asSequence()

    override fun remove(indexer: Long): Long =
            transactionEnviroment {
                Genres.deleteWhere { Genres.id eq indexer.toInt() }
            }.toLong()

    override fun getElement(indexer: Long): Genre = transactionEnviroment {
        Genres
                .select {Genres.id eq indexer.toInt()}
                .map { x -> Genre(x[Genres.id],x[Genres.name]) }
                .first()
    }

    override fun replace(indexer: Long, element: Genre): Long =
            transactionEnviroment {
                Genres.update({Genres.id eq indexer.toInt()}) {
                    it[Genres.name] = element.name
                }
            }.toLong()
}