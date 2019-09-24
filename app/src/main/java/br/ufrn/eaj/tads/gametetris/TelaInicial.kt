package br.ufrn.eaj.tads.gametetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tela_inicial.*

class TelaInicial : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        logoInicio.setImageResource(R.drawable.logo)
        imgpecas.setImageResource(R.drawable.pecas)

        novoJogo.setOnClickListener{
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        btnConfig.setOnClickListener {
            var i = Intent(this, ConfigGame::class.java)
            startActivity(i)
        }

    }
}
