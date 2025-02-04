package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // here we will now find view and assign it to variable.
        val weightText = findViewById<EditText>(R.id.etWeight)
        val heightText = findViewById<EditText>(R.id.etHeight)
        val calcButton = findViewById<Button>(R.id.btnCalculate)

        calcButton.setOnClickListener {
            val weight = weightText.text.toString()
            val height = heightText.text.toString()

            if (validateInput(weight,height)) {

                // code to calculate the body mass index
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                // getting the two decimal place output
                val bmi2digits = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2digits)
            }
        }
    }
    private fun validateInput(weight:String?, height:String?): Boolean{
        return when {
            weight.isNullOrEmpty()->{
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_LONG).show()
                return false
            }
            height.isNullOrEmpty()->{
                Toast.makeText(this, "height is empty", Toast.LENGTH_LONG).show()
                return false
            }
            else ->{
                true
        }
        }

    }
    @SuppressLint("SetTextI18n")
    private fun displayResult(bmi:Float){
        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        val info = findViewById<TextView>(R.id.tvInfo)

        resultIndex.text = bmi.toString()
        info.text = "(Normal range is 18.5 - 24.9)"

        var resultText = ""
        var color = 0

        when {
            bmi < 18.50 ->{
                resultText = "Underweight"
                color = R.color.underweight
            }
            bmi in 18.50..24.99->{
            resultText = "Healthy"
            color = R.color.normal
            }
            bmi in 25.00..29.99->{
                resultText = "Overweight"
                color = R.color.over_weight
            }
            bmi > 29.99->{
                resultText = "Obese"
                color = R.color.obese
            }
        }
        resultDescription.setTextColor(ContextCompat.getColor(this,color))
        resultDescription.text = resultText
    }
}
