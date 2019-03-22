package com.github.jmlb23.movielense.graphql.schema

import com.github.jmlb23.movielense.MovieLensService
import com.github.jmlb23.movielense.datasources.exposed.Genres
import com.github.jmlb23.movielense.datasources.exposed.Occupations
import com.github.jmlb23.movielense.datasources.exposed.Ratings
import com.github.jmlb23.movielense.domain.*
import com.github.jmlb23.movielense.repositories.*
import com.github.pgutkowski.kgraphql.KGraphQL
import com.github.pgutkowski.kgraphql.schema.model.FunctionWrapper
import org.joda.time.DateTime

val mlService = MovieLensService(MovieRepository, GenresRepository, OccupationRepository, RatingRepository, UserRepository)

val schema = KGraphQL.schema{
    //users
    query("allUsers"){
        mlService::getAllUsers.toResolver()
    }

    query("getUser"){
        mlService::getUser.toResolver()
    }

    mutation("createUser"){
        mlService::createUser.toResolver()
    }

    mutation("deleteUser"){
        mlService::deleteUser.toResolver()
    }

    mutation("updateUser"){
        mlService::updateUser.toResolver()
    }

    //Rates
    query("allRates"){
        resolver{
            -> RatingRepository.getAll().toList()
        }
    }

    query("getRate"){
        resolver{ id: Int
            -> RatingRepository.filter { Ratings.id eq id }.toList().first()
        }
    }

    mutation("createRate"){
        resolver{ userId: Long, movieId: Long, rating: Int ->
            RatingRepository.add(Rating(userId,movieId,rating,DateTime.now().toDate()))
        }
    }

    mutation("deleteRate"){
        resolver{id: Int ->
            RatingRepository.remove(id.toLong())
        }
    }

    mutation("updateRate"){
        resolver{ratingId: Int, userId: Long, movieId: Long, rating: Int ->
            val rate = Rating(userId,movieId,rating,DateTime.now().toDate())
            RatingRepository.replace(ratingId.toLong(),rate)
        }
    }
    //genres
    query("allGenres"){
        resolver{
            -> GenresRepository.getAll().toList()
        }
    }

    query("getGenre"){
        resolver{ id: Int
            -> GenresRepository.filter { Genres.id eq id }.toList().first()
        }
    }

    mutation("createGenre"){
        resolver{ id: Int, name: String->
            GenresRepository.add(Genre(id,name))
        }
    }

    mutation("deleteGenre"){
        resolver{id: Long ->
            GenresRepository.remove(id)
        }
    }

    mutation("updateGenre"){
        resolver{genereId: Long, name: String->
            val genre = Genre(0,name)
            GenresRepository.replace(genereId,genre)
        }
    }
    //occupation

    query("allOccupations"){
        resolver{
            -> OccupationRepository.getAll().toList()
        }
    }

    query("getOccupation"){
        resolver{ id: Int
            -> OccupationRepository.filter { Occupations.id eq id }.toList().first()
        }
    }

    mutation("createOccupation"){
        resolver{ id: Long, name: String->
            OccupationRepository.add(Occupation(id,name))
        }
    }

    mutation("deleteOccupation"){
        resolver{id: Long->
            OccupationRepository.remove(id)
        }
    }

    mutation("updateOccupation"){
        resolver{occupationId: Long, name: String ->
            val rate = Occupation(0,name)
            OccupationRepository.replace(occupationId,rate)
        }
    }

    inputType<InputUser>()
    type<User>()
    enum<Gender>()
    type<Rating>()
}