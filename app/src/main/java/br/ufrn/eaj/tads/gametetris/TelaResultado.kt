package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tela_resultado.*

class TelaResultado : AppCompatActivity() {


    val PREFS = "prefs_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_resultado)

        logoFim.setImageResource(R.drawable.logo)

        var params = intent.extras
        var texto = params?.getInt("pontos")

        textpontos.text = "$texto"

        val setting = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var edit = setting.edit()

        var record = setting.getInt("recorde", 0)

        textrecord.text = "$record"

        var pontuacaoAtual : Int = Integer.parseInt(texto.toString())


        if(pontuacaoAtual > record){

            newrecord.visibility = View.VISIBLE

            edit.putInt("recorde", pontuacaoAtual)
        }

        edit.commit()

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
