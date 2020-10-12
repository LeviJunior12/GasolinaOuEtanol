package com.example.aulalayout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var imageSet: ImageView
    lateinit var btnCalc: Button
    lateinit var editTextGas: EditText
    lateinit var editTextEthanol: EditText
    private var textGas: Float = 0f
    private var textEthanol: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        editTextGas = findViewById(R.id.editTextGas)
        editTextEthanol = findViewById(R.id.editTextEthanol)
        btnCalc = findViewById(R.id.calc_btn)
        imageSet = findViewById(R.id.imageResult)

        btnCalc.setOnClickListener(View.OnClickListener {
            calcResult()
        })

        editTextEthanol.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                calcResult()
                true
            }
            false
        }
    }

    private fun calcResult() {
        when {
            checkField() -> {
                getView().hideKeyboard()
                cleanField()
                Toast.makeText(this, getString(R.string.error_field), Toast.LENGTH_LONG)
                    .show()
            }
            else -> {
                getView().hideKeyboard()
                setImage()
                textGas = 0f
                textEthanol = 0f
            }
        }
    }

    private fun cleanField() {
        editTextGas.text.clear()
        editTextEthanol.text.clear()
        textGas = 0f
        textEthanol = 0f
        imageSet.setImageResource(R.drawable.empty_dice)
    }

    private fun checkField(): Boolean {
        if(!checkFieldBlank()) {
            textGas = editTextGas.text.toString().toFloat()
            textEthanol = editTextEthanol.text.toString().toFloat()
        }
        return checkFieldNumber()
    }

    private fun checkFieldBlank(): Boolean {
        return editTextGas.text.toString().isBlank() || editTextEthanol.text.toString().isBlank()
    }

    private fun checkFieldNumber(): Boolean {
        return textGas < 1 && textEthanol < 1
    }

    private fun setImage() {
        when {
            bestGas() -> {
                imageSet.setImageResource(R.drawable.abasteca_etanol)
            }
            else -> {
                imageSet.setImageResource(R.drawable.abasteca_gasolina)
            }
        }
    }

    private fun bestGas(): Boolean {
        return (textEthanol / textGas) < 0.7
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun getView(): View {
        return window.decorView.findViewById(android.R.id.content)
    }

}