package com.github.jmlb23.movielense.repositories

import com.github.jmlb23.movielense.datasources.exposed.Occupations
import com.github.jmlb23.movielense.datasources.exposed.transactionEnviroment
import com.github.jmlb23.movielense.domain.Occupation
import org.jetbrains.exposed.sql.*

object OccupationRepository : Repository<Occupation>{
    private fun ResultRow.toOccupation() = Occupation(this[Occupations.id].toLong(),this[Occupations.name])

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Occupation> = transactionEnviroment {
        Occupations.select(predicate).map{ it.toOccupation() }

    }.asSequence()

    override fun add(element: Occupation): Long =
            transactionEnviroment { Occupations.insert {
                it[name]= element.name
            }.generatedKey!!.toLong()
        }


    override fun getAll(): Sequence<Occupation> =
        transactionEnviroment {
            Occupations.selectAll()
                    .toList()
                    .map{ it.toOccupation() }
        }.asSequence()


    override fun remove(indexer: Long): Long =
        transactionEnviroment {
            Occupations.deleteWhere { Occupations.id eq indexer.toInt() }
        }.toLong()


    override fun replace(indexer: Long, element: Occupation): Long =
        transactionEnviroment {
            Occupations.update({Occupations.id eq indexer.toInt()}) {
                it[Occupations.name] = element.name
            }
        }.toLong()


    override fun getElement(indexer: Long): Occupation =
        transactionEnviroment {
            Occupations
                    .select {Occupations.id eq indexer.toInt()}
                    .map { it.toOccupation() }
                    .first()
        }


}