package com.example.ironsourcetest.presentation

data class ActionUi(val action: Action)

enum class Action{
    ANIMATION,
    TOAST,
    CALL,
    NOTIFICATION
}
