package com.example.aulalayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextGas = findViewById<EditText>(R.id.editTextGas).text
        val editTextEthanol = findViewById<EditText>(R.id. editTextEthanol).text
        val btnCalc = findViewById<Button>(R.id.calc_btn)
        val imageSet = findViewById<ImageView>(R.id.imageResult)

        btnCalc.setOnClickListener(View.OnClickListener {
            if(editTextGas.isEmpty() || editTextEthanol.isEmpty()) {
                getView().hideKeyboard()
                Toast.makeText(this, "Por favor Complete Os Campos Acima!", Toast.LENGTH_LONG).show()
            } else {
                getView().hideKeyboard()
                setImage(editTextGas.toString(), editTextEthanol.toString(), imageSet)
            }

        })
    }

    private fun setImage(gas: String, ethanol: String, image: ImageView) {
        if(bestGas(gas, ethanol)) {
            image.setImageResource(R.drawable.abasteca_etanol)
        } else {
            image.setImageResource(R.drawable.abasteca_gasolina)
        }
    }

    private fun bestGas(gas: String, ethanol: String): Boolean {
        return (ethanol.toFloat() / gas.toFloat()) <  0.7
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getView(): View {
        return window.decorView.findViewById<View>(android.R.id.content)
    }

}