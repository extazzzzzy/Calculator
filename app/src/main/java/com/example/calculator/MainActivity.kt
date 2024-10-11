package com.example.calculator

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.mvel2.MVEL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val buttonToValueMap = mapOf(
            R.id.buttonPlus to "+",
            R.id.buttonMinus to "-",
            R.id.buttonStar to "*",
            R.id.buttonPoint to ".",
            R.id.buttonSlash to "/",
            R.id.buttonZero to "0",
            R.id.buttonOne to "1",
            R.id.buttonTwo to "2",
            R.id.buttonThree to "3",
            R.id.buttonFour to "4",
            R.id.buttonFive to "5",
            R.id.buttonSix to "6",
            R.id.buttonSeven to "7",
            R.id.buttonEight to "8",
            R.id.buttonNine to "9",

            R.id.buttonClear to "Clear",
            R.id.buttonEquals to "Equals",
        )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtonListeners(editText, buttonToValueMap)
    }


    fun setupButtonListeners(editText: EditText, buttonToValueMap: Map<Int, String>) {
        buttonToValueMap.keys.forEach { buttonId ->
            val button = findViewById<ImageButton>(buttonId)

            button.setOnClickListener {
                val value = buttonToValueMap[buttonId]

                if (value != "Clear" && value != "Equals")
                {
                    if (value == "/" || value == "+" || value == "-" || value == ".")
                    {
                        val lastSymbol = editText.text.toString().substring(editText.text.toString().length - 1, editText.text.toString().length)
                        if (lastSymbol != "/" && lastSymbol != "*" && lastSymbol != "-" && lastSymbol != "+" && lastSymbol != ".")
                        {
                            editText.append(value)
                        }
                    }
                    else if (value == "*")
                    {
                        val lastSymbol = editText.text.toString().substring(editText.text.toString().length - 1, editText.text.toString().length)
                        val lastSecondSymbol = editText.text.toString().substring(editText.text.toString().length - 2, editText.text.toString().length - 1)

                        if ((lastSymbol != "/" && lastSymbol != "*" && lastSymbol != "-" && lastSymbol != "+" && lastSymbol != ".") || (lastSymbol == "*" && lastSecondSymbol != "*"))
                        {
                            editText.append(value)
                        }
                    }
                    else
                    {
                        editText.append(value)
                    }
                }
                else if (value == "Clear")
                {
                    val currentText = editText.text.toString()
                    if (currentText.isNotEmpty())
                    {
                        val newText = currentText.substring(0, currentText.length - 1)
                        editText.setText(newText)
                        editText.setSelection(newText.length)
                    }
                }
                else
                {
                    val lastSymbol = editText.text.toString().substring(editText.text.toString().length - 1, editText.text.toString().length)
                    if (lastSymbol != "+" && lastSymbol != "-" && lastSymbol != "*" && lastSymbol != "/" && lastSymbol != ".")
                    {
                        val expression = editText.text.toString()
                        val result = MVEL.eval(expression)
                        editText.setText(result.toString())
                    }
                }
            }

            button.setOnLongClickListener {
                val value = buttonToValueMap[buttonId]

                if (value == "Clear")
                {
                    editText.text = null
                }
                true
            }
        }
    }


}