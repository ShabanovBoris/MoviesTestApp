package com.bosha.core_data.mappers

import com.bosha.core.Mapper
import com.bosha.core_data.datasource.remote.impl.MoviesRemoteDataSource.Companion.baseImageBackdropUrl
import com.bosha.core_data.dto.remote.JsonActor
import com.bosha.core_domain.entities.Actor
import javax.inject.Inject


class ActorNetworkMapper @Inject constructor(): Mapper<JsonActor, Actor> {
    override fun toDomainEntity(data: JsonActor, page: Int?): Actor =  Actor(
        id = data.id,
        name = data.name,
        imageUrl = baseImageBackdropUrl + data.profilePicture)

    override fun toDataEntity(domain: Actor): JsonActor {
        throw NotImplementedError()
    }
}