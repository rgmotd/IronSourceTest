package com.example.ironsourcetest.core

import com.example.ironsourcetest.data.ActionData
import com.example.ironsourcetest.data.ActionSharedPreferences
import com.example.ironsourcetest.domain.ActionDomain
import com.example.ironsourcetest.presentation.Action
import com.example.ironsourcetest.presentation.ActionUi
import java.util.*

interface Mapper<S, T> {

    fun map(data: S): T

    class ActionDataToDomainMapper(
        private val sharedPreferences: ActionSharedPreferences
    ) : Mapper<List<ActionData>, List<ActionDomain>> {

        override fun map(data: List<ActionData>): List<ActionDomain> {
            return data.map { action ->
                ActionDomain(
                    coolDown = action.coolDown,
                    enabled = action.enabled,
                    type = action.type,
                    priority = action.priority,
                    validDays = action.validDays,
                    lastShown = sharedPreferences.getLastShownTimeForAction(action.type)
                )
            }
        }
    }

    class ActionDomainToUiMapper(
        private val sharedPreferences: ActionSharedPreferences
    ) : Mapper<List<ActionDomain>, ActionUi?> {

        override fun map(data: List<ActionDomain>): ActionUi? {
            val actionWithMaxPriority = findMaxPriority(data)
            val isDayAvailable = isDayAvailable(actionWithMaxPriority.validDays)
            val isEnabled = actionWithMaxPriority.enabled
            val isCoolDownPassed =
                isCoolDownPassed(actionWithMaxPriority.coolDown, actionWithMaxPriority.lastShown)
            return if (isDayAvailable && isEnabled && isCoolDownPassed) {
                sharedPreferences.setLastShownTimeForAction(actionWithMaxPriority.type)
                ActionUi(Action.valueOf(actionWithMaxPriority.type.uppercase(Locale.getDefault())))
            } else null
        }

        private fun isCoolDownPassed(coolDown: Long, lastShown: Long): Boolean {
            return System.currentTimeMillis() - lastShown > coolDown
        }

        private fun findMaxPriority(data: List<ActionDomain>): ActionDomain {
            return data.reduce(Compare::max)
        }

        private fun isDayAvailable(days: List<Int>): Boolean {
            val calendar: Calendar = Calendar.getInstance()
            val day: Int = calendar.get(Calendar.DAY_OF_WEEK)
            return (day - 1) in days
        }

        internal object Compare {
            fun max(a: ActionDomain, b: ActionDomain): ActionDomain {
                return if (a.priority > b.priority) a else b
            }
        }
    }
}