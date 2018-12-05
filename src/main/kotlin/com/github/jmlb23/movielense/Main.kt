package com.github.jmlb23.movielense

import com.github.jmlb23.movielense.graphql.helper.Gateway
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main(args: Array<String>) {

    Gateway.start().asServer(Jetty(8080)).start()
}
