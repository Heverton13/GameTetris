package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_tela_resultado.*

class TelaResultado : AppCompatActivity() {


    val PREFS = "prefs_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_resultado)

        logoFim.setImageResource(R.drawable.logo)

        var params = intent.extras
        var texto = params?.getInt("pontos")

        textpontos.text = "Pontuação: $texto"


        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var editor = settings.edit()
        editor.putString("score", texto.toString())
        editor.commit()


        var record = settings.getString("score",texto.toString())

        if(texto.toString().toInt() > record.toString().toInt()){

            Log.i("s","Esse aqui é pra valer")
            newrecord.visibility= View.VISIBLE
            editor.putInt("score", texto.toString().toInt())
            editor.commit()

        }

        textrecord.text = "Record: $record"

        btnNewGame.setOnClickListener {
            var x = Intent(this, MainActivity::class.java)
            startActivity(x)
            finish()
        }

        btnSair.setOnClickListener {
            finish()
        }
    }
}
