package br.ufrn.eaj.tads.gametetris

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_config_game.*

class ConfigGame : AppCompatActivity() {

    val PREFS = "prefs_file"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_game)

        logoconfig.setImageResource(R.drawable.logo)

        btConfirme.setOnClickListener {
            finish()
        }

    }

    override fun onStop() {
        super.onStop()

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        var editor = settings.edit()

        if(radioFacil.isChecked){
            editor.putBoolean("salvar", radioFacil.isChecked)
            editor.putString("texto", "400")
            editor.commit()
        }else if (radioMedio.isChecked){
            editor.putBoolean("salvar", radioMedio.isChecked)
            editor.putString("texto", "280")
            editor.commit()
        }else if (radioDificil.isChecked){
            editor.putBoolean("salvar", radioDificil.isChecked)
            editor.putString("texto", "150")
            editor.commit()
        }else{
            editor.remove("salvar")
            editor.remove("texto")
            editor.commit()
        }

    }

}
