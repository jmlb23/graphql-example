package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.domain.Genres
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder

object GenresRepository : Repository<Genres>{
    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Genres> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun add(element: Genres): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Sequence<Genres> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(indexer: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun replace(indexer: Long, element: Genres): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getElement(indexer: Long): Genres {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}