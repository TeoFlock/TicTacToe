package com.example.android.tictactoe.Data

data class Field(val row: Int, val column: Int, var player: Player? = null) {

    override fun equals(other: Any?): Boolean {
        if (other !is Field) {return super.equals(other)}

        return this.row == other.row && this.column == other.column
    }
}

data class Player(val name: String, val sign: Char) {
    var score = 0
}

