package de.kallifabio.testapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "x"
    private var gameState = "playing"
    private lateinit var allFields: Array<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allFields = arrayOf(field0, field1, field2, field3, field4, field5, field6, field7, field8)

        for (field in allFields) {
            field.setOnClickListener {
                onFieldClick(it as TextView)
            }
        }
    }

    private fun onFieldClick(field: TextView) {
        if (gameState != "playing") {
            resetGame()
            return
        }

        if (field.text == "") {
            field.text = currentPlayer
            if (checkWin()) {
                statusText.text = "Der Spieler $currentPlayer hat gewonnen!"
                gameState = "won"
            } else if (allFields.all { it.text != "" }) {
                statusText.text = "Unentschieden! Kein Spieler hat gewonnen!"
                gameState = "tie"
            } else {
                currentPlayer = if (currentPlayer == "x") "o" else "x"
                statusText.text = "Der Spieler $currentPlayer ist an der Reihe!"
            }

        }
    }

    private fun resetGame() {
        currentPlayer = "x"
        statusText.text = "Der Spieler $currentPlayer ist an der Reihe"
        gameState = "playing"

        for (field in allFields) {
            field.text = ""
        }
    }

    private fun checkWin(): Boolean {
        val horizontal =
            (field0.text == field1.text && field1.text == field2.text && field0.text != "") ||
                    (field3.text == field4.text && field4.text == field5.text && field3.text != "") ||
                    (field6.text == field7.text && field7.text == field8.text && field6.text != "")

        val vertical =
            (field0.text == field3.text && field3.text == field6.text && field0.text != "") ||
                    (field1.text == field4.text && field4.text == field7.text && field1.text != "") ||
                    (field2.text == field5.text && field5.text == field8.text && field2.text != "")

        val leftTopToRightBottom =
            (field0.text == field4.text && field4.text == field8.text && field0.text != "")

        val rightTopToLeftBottom =
            (field2.text == field4.text && field4.text == field6.text && field2.text != "")

        return horizontal || vertical || leftTopToRightBottom || rightTopToLeftBottom
    }
}