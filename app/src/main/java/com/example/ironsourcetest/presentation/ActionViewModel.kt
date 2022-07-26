package com.example.ironsourcetest.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ironsourcetest.ActionsRepository
import com.example.ironsourcetest.core.Result
import com.example.ironsourcetest.core.Mapper
import kotlinx.coroutines.launch

class ActionViewModel(
    private val repository: ActionsRepository,
    private val mapper: Mapper.ActionDomainToUiMapper
) : ViewModel() {
    private val _action = MutableLiveData<Result<ActionUi>>()
    val action: LiveData<Result<ActionUi>> = _action

    fun doAction() = viewModelScope.launch {
        when (val actions = repository.getActions()) {
            is Result.Success -> {
                val actionUi = mapper.map(actions.result)
                if (actionUi == null) {
                    _action.postValue(Result.Failure("error occurred"))
                } else {
                    _action.postValue(Result.Success(actionUi))
                }
            }
            is Result.Failure -> {
                _action.postValue(Result.Failure(actions.error))
            }
        }
    }
}