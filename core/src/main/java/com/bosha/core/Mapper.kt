package com.bosha.core


interface Mapper<DATA, DOMAIN> {

    fun toDomainEntity(data: DATA, page: Int?): DOMAIN

    fun toDataEntity(domain: DOMAIN): DATA

    fun toDomainEntityList(list: List<DATA>): List<DOMAIN>{
        // page uses only for raw query
        return list.map { toDomainEntity(it, 0) }
    }

    fun toDataEntityList(list: List<DOMAIN>): List<DATA>{
        return list.map { toDataEntity(it) }
    }
}

