package com.example.ironsourcetest

import com.example.ironsourcetest.core.Mapper
import com.example.ironsourcetest.core.Result
import com.example.ironsourcetest.data.ActionService
import com.example.ironsourcetest.domain.ActionDomain
import java.lang.Exception

interface ActionsRepository {

    suspend fun getActions(): Result<List<ActionDomain>>

    class Base(
        private val actionService: ActionService,
        private val mapper: Mapper.ActionDataToDomainMapper,
    ) : ActionsRepository {

        override suspend fun getActions(): Result<List<ActionDomain>> {
            return try {
                val actions = actionService.getActions()
                if (actions.isSuccessful) {
                    Result.Success(mapper.map(actions.body() ?: mutableListOf()))
                } else {
                    Result.Failure("unknown error")
                }
            } catch (e: Exception) {
                Result.Failure(e.localizedMessage ?: "unknown error")
            }
        }
    }
}