package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.domain.Movie
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

object MovieRepository : Repository<Movie>{
    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: Movie): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Sequence<Movie> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(indexer: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replace(indexer: Long, element: Movie): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getElement(indexer: Long): Movie {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}