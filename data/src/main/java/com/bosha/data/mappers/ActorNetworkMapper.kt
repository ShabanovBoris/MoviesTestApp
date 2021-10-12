package com.bosha.data.mappers

import com.bosha.data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImageBackdropUrl
import com.bosha.data.dto.remote.JsonActor
import com.bosha.domain.entities.Actor
import com.bosha.domain.utils.Mapper
import javax.inject.Inject


class ActorNetworkMapper @Inject constructor(): Mapper<JsonActor, Actor> {
    override fun toDomainEntity(data: JsonActor): Actor =  Actor(
        id = data.id,
        name = data.name,
        imageUrl = baseImageBackdropUrl + data.profilePicture)

    override fun toDataEntity(domain: Actor): JsonActor {
        throw NotImplementedError()
    }
}