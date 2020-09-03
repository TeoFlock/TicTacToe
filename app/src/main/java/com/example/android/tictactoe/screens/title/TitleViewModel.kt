package com.example.android.tictactoe.screens.title

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TitleViewModel : ViewModel() {
    private val _player1Name = MutableLiveData<String>()
    val player1Name: LiveData<String>
        get() = _player1Name

    private val _player2Name = MutableLiveData<String>()
    val player2Name: LiveData<String>
        get() = _player2Name

    private val _editNamesFinish = MutableLiveData<Boolean>()
    val editNamesFinish: LiveData<Boolean>
        get() = _editNamesFinish

    fun onTitleFinish() {
        _editNamesFinish.value = true
    }

    fun onTitleFinishDone() {
        _editNamesFinish.value = false
    }

    fun enterPlayer1Name(name: String) {
        _player1Name.value = name
    }

    fun enterPlayer2Name(name: String) {
        _player2Name.value = name
    }

    init {
        _editNamesFinish.value = false
    }
}