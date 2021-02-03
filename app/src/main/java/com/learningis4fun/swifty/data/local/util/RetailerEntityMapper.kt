package com.learningis4fun.swifty.data.local.util

import com.learningis4fun.swifty.data.Retailer
import com.learningis4fun.swifty.data.local.entity.RetailerEntity

class RetailerEntityMapper : EntityMapper<RetailerEntity, Retailer> {
    override fun mapFromEntity(entity: RetailerEntity): Retailer {
        return Retailer(
            id = entity.id,
            name = entity.name,
            location = entity.location
        )
    }

    override fun mapFromModel(model: Retailer): RetailerEntity {
        return RetailerEntity(
            id = model.id,
            name = model.name,
            location = model.location
        )
    }

    fun mapFromEntities(entities: List<RetailerEntity>): List<Retailer> {
        return entities.map {
            mapFromEntity(it)
        }
    }

    fun mapFromModels(models: List<Retailer>): List<RetailerEntity> {
        return models.map {
            mapFromModel(it)
        }
    }
}