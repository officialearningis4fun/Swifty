package com.learningis4fun.swifty.data.local.util

interface EntityMapper <Entity, Model>{

    fun mapFromEntity(entity: Entity) : Model
    fun mapFromModel(model: Model) : Entity
}