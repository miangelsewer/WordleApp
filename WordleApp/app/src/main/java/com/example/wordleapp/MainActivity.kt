package com.example.wordleapp
import nl.dionsegijn.konfetti.xml.KonfettiView
import nl.dionsegijn.konfetti.core.models.Size
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import nl.dionsegijn.konfetti.core.Party
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    var countGuess = 0
    val maxGuess = 3
    val wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.guessButton)
        val input = findViewById<TextInputEditText>(R.id.guessInput)

        val guessWords = listOf(
            findViewById<TextView>(R.id.guessWord1),
            findViewById<TextView>(R.id.guessWord2),
            findViewById<TextView>(R.id.guessWord3)

        )
        val guessChecks = listOf(
            findViewById<TextView>(R.id.guessCheck1),
            findViewById<TextView>(R.id.guessCheck2),
            findViewById<TextView>(R.id.guessCheck3)
        )

        val answerView = findViewById<TextView>(R.id.answerView)
        answerView.text = ""
        answerView.visibility = View.INVISIBLE

        val konfettiView = findViewById<KonfettiView>(R.id.konfettiView)

        button.setOnClickListener{
            val guess = input.text.toString().uppercase()
            if (guess.length!= 4 ){
                Toast.makeText(it.context, "Please enter a 4 letter Word!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(countGuess >= maxGuess){
                Toast.makeText(it.context, "Exceeded number of guesses!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val feedback = checkGuess(guess)
            guessWords[countGuess].text = guess
            guessChecks[countGuess].text = feedback
            if (guess == wordToGuess) {
                konfettiView.start(
                    listOf(
                        Party(
                            speed = 0f,
                            maxSpeed = 30f,
                            damping = 0.9f,
                            spread = 360,
                            colors = listOf(Color.YELLOW, Color.GREEN, Color.MAGENTA),
                            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).perSecond(100),
                            position = Position.Relative(0.5, 0.3),
                            size = listOf(Size.SMALL, Size.MEDIUM)
                        )
                    )
                )
            }
            countGuess++

            input.text?.clear()
            if(countGuess == maxGuess){
                answerView.text = "Answer: $wordToGuess"
                answerView.visibility = View.VISIBLE
            }
        }

    }
    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    }