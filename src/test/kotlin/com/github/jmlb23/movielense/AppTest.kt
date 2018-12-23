package com.github.jmlb23.movielense

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.github.jmlb23.movielense.domain.Occupation
import com.github.jmlb23.movielense.graphql.schema.schema
import org.junit.Assert
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
        }.fold({}, {})
        )
    }

    @Test
    fun testMutationOnUser() {
        schema.execute("""mutation{
                createUser(age: 27, gender: "M", occupationId: 5, zipCode: "15704")
            }

            """.trimIndent()
        )
    }

    @Test
    fun testMutationOnRating() {

        data class Element(val id: Int)
        data class Data(val allRates: List<Element>)
        data class Obj(val data: Data)

        data class Data2(val createRate: Int)
        data class Obj2(val data: Data2)

        val idJson = schema.execute("""
                    mutation{
                        createRate(userId: 944, movieId: 1, rating: 5)
                    }
                """.trimIndent())

        val it = schema.execute("""
            query {
                allRates{
                    id
                }
            }""")

        val list = Klaxon().parse<Obj>(it)
        val id = Klaxon().parse<Obj2>(idJson)

        val last = list?.data?.allRates?.last()

        Assert.assertTrue(id?.data?.createRate == last?.id)


    }

    @Test
    fun testMutationOnUserForDelete(){
        data class Data(val getUser: String? = null)
        data class Response(val data: Data?)

        schema.execute("mutation {deleteUser(id: 944)}")
        Assert.assertNull(
        Klaxon().parse<Response>(schema.execute(
                """query{
                        |getUser(id: 944){
                            | id
                            | gender
                        |}
                    |}""".trimMargin()))?.data?.getUser)
    }

    @Test
    fun testMutationOnUserForUpdate(){
        data class User(val id: Int, val age: Int, val gender: String, val occupationId: Long, val zipCode: String)
        data class Data(val getUser: User)
        data class Response(val data: Data)
        val id = 942
        val updateObject = User(id, 25, "M", 4, "14500")

        val response = schema.execute("mutation{updateUser(id: $id, age: ${updateObject.age}, gender: \"${updateObject.gender}\", occupationId: ${updateObject.occupationId}, zipCode: \"${updateObject.zipCode}\" )}")

        val ResponseObj = Klaxon().parse<Response>(schema.execute("""query{getUser(id: $id){
            |id
            |age
            |gender
            |occupationId
            |zipCode
            |}}""".trimMargin()))
        Assert.assertEquals(ResponseObj!!.data.getUser,updateObject)

    }
}
