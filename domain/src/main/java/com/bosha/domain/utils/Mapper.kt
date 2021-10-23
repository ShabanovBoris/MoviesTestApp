package com.bosha.domain.utils


interface Mapper<DATA, DOMAIN> {

    fun toDomainEntity(data: DATA): DOMAIN

    fun toDataEntity(domain: DOMAIN): DATA

    fun toDomainEntityList(list: List<DATA>): List<DOMAIN>{
        return list.map { toDomainEntity(it) }
    }

    fun toDataEntityList(list: List<DOMAIN>): List<DATA>{
        return list.map { toDataEntity(it) }
    }
}

