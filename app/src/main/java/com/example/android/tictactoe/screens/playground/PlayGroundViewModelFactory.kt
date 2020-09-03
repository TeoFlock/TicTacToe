package com.example.android.tictactoe.screens.playground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayGroundViewModelFactory(private val player1Name: String, private val player2Name: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaygroundViewModel::class.java)) {
            return PlaygroundViewModel(player1Name, player2Name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}