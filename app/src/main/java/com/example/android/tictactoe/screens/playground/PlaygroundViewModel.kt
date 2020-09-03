package com.example.android.tictactoe.screens.playground

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.tictactoe.Data.Field
import com.example.android.tictactoe.Data.Player
import java.lang.IllegalArgumentException

class PlaygroundViewModel(val player1Name: String, val player2Name: String) : ViewModel() {
    val player1 = Player(player1Name, 'X')
    val player2 = Player(player2Name, 'O')

    private val fields = mutableListOf<Field>()

    private val _currentPlayer = MutableLiveData<Player>()
    val currentPlayer: LiveData<String> = Transformations.map(_currentPlayer) { player ->
        player.name
    }
    val currentPlayerSign: LiveData<String> = Transformations.map(_currentPlayer) { player ->
        player.sign.toString()
    }

    private val _chosenFieldKey = MutableLiveData<String>()
    val chosenFieldKey: LiveData<String>
        get() = _chosenFieldKey

    private val _currentPlayerScored = MutableLiveData<Boolean>()
    val currentPlayerScored: LiveData<Boolean>
        get() = _currentPlayerScored

    var runs = 0

    init {
        initFields()

        _currentPlayer.value = player1
        _currentPlayerScored.value = false
    }

    fun onButtonClick(buttonKey: String) {
        val success = setField(buttonKey)

        if(success) {
           runs++

           onFieldChosen(buttonKey)

           if(checkForWinner() != null) {
               onScored()
           }

           switchCurrentPlayer()
        }

        if(runs == 9) {
            clearPlayground()
        }
    }

    fun onScored() {
        _currentPlayerScored.value = true
    }

    fun onScoredDone() {
        _currentPlayerScored.value = false
    }

    fun onFieldChosen(fieldKey: String) {
        _chosenFieldKey.value = fieldKey
    }

    fun setField(field: Field): Boolean {
        fields.find { it == Field(field.row, field.column) }.apply {
            if (this?.player == null) {
                this?.player = _currentPlayer.value
                return true
            }
            return false
        }
    }

    fun setField(field: String): Boolean {
        if(field.length != 2) {
            throw IllegalArgumentException("Illegal value for field! Must have 2 characters")
        }
        val row = when (field[0]) {
            'a' -> 0
            'b' -> 1
            'c' -> 2
            else -> throw IllegalArgumentException("Illegal value for field! First entry must be from [a, b, c]")
        }
        val col = field[1].toString().toInt()
        if(col > 3 || col < 1) {
            throw IllegalArgumentException("Illegal value for field! Last entry must be 0 < int < 4")
        }

        return setField(Field(row, col))
    }

    fun clearPlayground() {
        fields.forEach { it.player = null }
        runs = 0
    }

    fun switchCurrentPlayer() {
        _currentPlayer.value = if (_currentPlayer.value === player1) {
            player2
        } else {
            player1
        }
    }

    fun checkForWinner(): Player? {
        if(runs < 5) {
            return null
        }

        val fieldsLine = mutableListOf<Field?>()

        for(col in 0..2) {
            fieldsLine.clear()
            for(row in 0..2) {
                fieldsLine.add(fields.find {
                    it == Field(row, col)
                })
            }
            if(fieldsLine.threeInRow()) {
                return fieldsLine[0]?.player
            }
        }

        for(row in 0..2) {
            fieldsLine.clear()
            for(col in 0..2) {
                fieldsLine.add(fields.find {
                    it == Field(row, col)
                })
            }
            if(fieldsLine.threeInRow()) {
                return fieldsLine[0]?.player
            }
        }

        fieldsLine.clear()
        for(diagonalIdx in 0..2) {
            fieldsLine.add(fields.find {
                it == Field(diagonalIdx, diagonalIdx)
            })
        }

        if(fieldsLine.threeInRow()) {
            return fieldsLine[0]?.player
        }

        fieldsLine.clear()
        var row = 0
        var col = 2
        while(row < 3) {
            fieldsLine.add(fields.find {
                it == Field(row, col)
            })
            row++
            col--
        }
        if(fieldsLine.threeInRow()) {
            return fieldsLine[0]?.player
        }
        return null
    }

    private fun MutableList<Field?>.threeInRow(): Boolean {
        if(this.size != 3) {
            throw IllegalArgumentException("List must have 3 entries!")
        }
        val firstFieldPlayer: Player? = this[0]?.player
        return this.filter { it?.player === firstFieldPlayer }.size == 3
    }

    private fun initFields() {
        for (row in 0..2) {
            for (col in 0..2) {
                fields.add(Field(row, col, null))
            }
        }
    }
}