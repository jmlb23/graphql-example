package com.github.jmlb23.movielense.graphql.helper

import com.github.jmlb23.movielense.graphql.schema.schema
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

object Gateway{
    fun start(): HttpHandler = routes(
            "/graphql" bind Method.POST to { req: Request ->
                val res = schema.runCatching{
                    execute(req.bodyString())
                }
                val response = res.fold(onSuccess = {it}, onFailure = {"""
                    {
                        "errors": ${it.message}
                    }
                """.trimIndent()})
                Response(Status.OK).body(response)
            }

    )
}