package com.github.jmlb23.movielense

import com.github.jmlb23.movielense.graphql.schema.schema
import kotlin.test.Test

class AppTest {
    @Test
    fun testAppHasAGreeting() {
        println(schema.runCatching {
            this.execute("""query {allUsers{
                        age
                        gender
                        }
            }
                """.trimIndent())
        }.fold({},{})
        )
    }
}
