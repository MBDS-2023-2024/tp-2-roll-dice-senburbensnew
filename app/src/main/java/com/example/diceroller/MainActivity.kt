package com.example.diceroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * This activity allows the user to roll a dice and view the result
 * on the screen.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var rollButton: Button
    private lateinit var guessedNumberEditView: EditText
    private lateinit var resultTextView1: TextView
    private lateinit var resultTextView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        rollButton = findViewById(R.id.button)
        guessedNumberEditView = findViewById(R.id.editText)
        resultTextView1 = findViewById(R.id.textView1)
        resultTextView2 = findViewById(R.id.textView2)

        rollButton.isEnabled = false
        setInitialTextViewHints()
    }

    @SuppressLint("SetTextI18n")
    private fun setInitialTextViewHints() {
        resultTextView1.text = "dice1"
        resultTextView2.text = "dice2"
    }

    private fun setupListeners() {
        rollButton.setOnClickListener { rollDice() }
        guessedNumberEditView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                rollButton.isEnabled = isInputValid(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isInputValid(input: CharSequence?): Boolean {
        return input?.let {
            val number = it.toString().toIntOrNull()
            number != null && number in 2..12
        } == true
    }

    private fun rollDice() {
        val dice1 = Dice(6).roll()
        val dice2 = Dice(6).roll()

        val guessedNumber = guessedNumberEditView.text.toString().toIntOrNull()

        if (guessedNumber == null || guessedNumber !in 2..12) {
            showToast("Veuillez entrer un nombre valide entre 2 et 12")
            return
        }

        updateResultViews(dice1, dice2)

        if (dice1 + dice2 == guessedNumber) {
            showToast("Félicitations ! Vous avez gagné !")
        } else {
            showToast("Essayez encore !")
        }
    }

    private fun updateResultViews(dice1: Int, dice2: Int) {
        resultTextView1.text = dice1.toString()
        resultTextView2.text = dice2.toString()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    class Dice(private val numSides: Int) {
        fun roll(): Int {
            return (1..numSides).random()
        }
    }
}