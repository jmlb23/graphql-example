package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.datasources.exposed.Genres
import com.github.jmlb23.movielense.domain.Genre
import org.jetbrains.exposed.sql.*

object GenresRepository : Repository<Genre>{
    private fun ResultRow.toGenre() = Genre(this[Genres.id],this[Genres.name])

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Genre> = transactionEnviroment {
        Genres.select(predicate).map{ it.toGenre() }

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
                .map{ it.toGenre() }
    }.asSequence()

    override fun remove(indexer: Long): Long =
            transactionEnviroment {
                Genres.deleteWhere { Genres.id eq indexer.toInt() }
            }.toLong()

    override fun getElement(indexer: Long): Genre = transactionEnviroment {
        Genres
                .select {Genres.id eq indexer.toInt()}
                .map { it.toGenre() }
                .first()
    }

    override fun replace(indexer: Long, element: Genre): Long =
            transactionEnviroment {
                Genres.update({Genres.id eq indexer.toInt()}) {
                    it[Genres.name] = element.name
                }
            }.toLong()
}