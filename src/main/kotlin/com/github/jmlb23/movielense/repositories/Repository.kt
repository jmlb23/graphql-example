package com.github.jmlb23.movielense.repositories

import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder


interface Repository<D>{
    fun add(element: D): Long
    fun getAll(): Sequence<D>
    fun remove(indexer: Long): Long
    fun replace(indexer: Long, element: D): Long
    fun getElement(indexer: Long): D?
    fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<D>
}