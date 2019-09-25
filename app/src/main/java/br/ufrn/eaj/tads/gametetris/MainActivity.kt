package br.ufrn.eaj.tads.gametetris

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import classes.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed:Long = 500
    var pontuacao = 0

    var pt:Peca = getRadomPeca()

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    val PREFS = "prefs_file"
    var valorpadrao:Long = 0
    var pausa = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA


        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }

        //Retornando a velocidade da configuração

        val settings = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val texto = settings.getString("texto", "")

        if (texto != null) {
            valorpadrao = texto.toLong()
            speed = texto.toLong()
        }

        buttonLeft.setOnClickListener {
            if(verificarParadaDireita())
            pt.moveLeft()
        }

        buttonRight.setOnClickListener {
            if(verificarParadaEsquerda())
            pt.moveRight()
        }

        buttonRotate.setOnClickListener {
            if(verificarParadaDireita() && verificarParadaEsquerda())
            pt.rotate()
        }

        btnNewGame.setOnClickListener {
            speed -= 100
        }

        btnPause.setOnClickListener {
            if (pausa == true) {

                running = false
                pausa = false

            } else {

                running = true
                pausa = true
                gameRun()
            }
        }

        gameRun()

    }

    fun gameRun(){
        Thread{
            while(running){
                Thread.sleep(speed)
                runOnUiThread{
                    //limpa tela
                    for (i in 0 until LINHA) {
                        for (j in 0 until COLUNA) {
                            // Verifica a board, onde for 1 deixa branco onde for zero permanece preto
                            when (board[i][j]) {
                                0 -> {
                                    boardView[i][j]!!.setImageResource(R.drawable.black)
                                }
                                1 -> {
                                    boardView[i][j]!!.setImageResource(R.drawable.white)
                                }
                            }
                        }
                    }
                    //move peça atual

                    if(verificarParada()){
                        pt.moveDown()
                        pintarBord()
                    }else {
                        speed = valorpadrao
                        pintarBord()
                        // preenche a board onde a peça parou com 1, para que seja mantido sua posição e visualização com vir a outra peça
                        board[pt.pontoA.x][pt.pontoA.y] = 1
                        board[pt.pontoB.x][pt.pontoB.y] = 1
                        board[pt.pontoC.x][pt.pontoC.y] = 1
                        board[pt.pontoD.x][pt.pontoD.y] = 1

                        if(running) {gameOver()}
                        pt = getRadomPeca()

                    }
                    verificarLinha()

                }
            }
        }.start()
    }

    // Função que verficar se a peça chegou no "chão"
    fun verificarParada(): Boolean{

        return (pt.pontoA.x + 1  < LINHA && board[pt.pontoA.x + 1][pt.pontoA.y] != 1) &&
                (pt.pontoB.x + 1 < LINHA && board[pt.pontoB.x + 1][pt.pontoB.y] != 1) &&
                (pt.pontoC.x + 1 < LINHA && board[pt.pontoC.x + 1][pt.pontoC.y] != 1) &&
                (pt.pontoD.x + 1 < LINHA && board[pt.pontoD.x + 1][pt.pontoD.y] != 1)
    }

    //função que verfica se a peça passou da parede lado direito

    fun verificarParadaDireita(): Boolean{
        return ((pt.pontoA.y + 1 < COLUNA && board[pt.pontoA.x][pt.pontoA.y + 1] != 1) &&
                (pt.pontoB.y + 1 < COLUNA && board[pt.pontoB.x][pt.pontoB.y + 1] != 1) &&
                (pt.pontoC.y + 1 < COLUNA && board[pt.pontoC.x][pt.pontoC.y + 1] != 1) &&
                (pt.pontoD.y + 1 < COLUNA && board[pt.pontoD.x][pt.pontoD.y + 1] != 1))
    }


    //função que verfica se a peça passou da parede lado esquerdo
    fun verificarParadaEsquerda(): Boolean{

        return ((pt.pontoA.y - 1 >=  0  && board[pt.pontoA.x][pt.pontoA.y - 1] < 1) &&
                (pt.pontoB.y - 1 >=  0  && board[pt.pontoB.x][pt.pontoB.y - 1] < 1) &&
                (pt.pontoC.y - 1 >=  0  && board[pt.pontoC.x][pt.pontoC.y - 1] < 1) &&
                (pt.pontoD.y - 1 >=  0  && board[pt.pontoD.x][pt.pontoD.y - 1] < 1))
    }

    fun gameOver(){

        for (i in 0 until COLUNA) {
            if (board[4][i] == 1) {
                running = false
                var x = Intent(this, TelaResultado::class.java)
                var b = Bundle()
                b.putInt("pontos", pontuacao)
                x.putExtras(b)
                startActivity(x)
                finish()
                break
            }
        }
    }

    // Função para gerar peças aleatórias
    fun getRadomPeca():Peca {


        var x = Random.nextInt(0, 6)

        return when(x) {
            0 -> {
                return L(1, 12)
            }
            1 -> {
                return T(1, 12)
            }
            2 -> {
                return I(1, 12)
            }
            3 -> {
                return L2(1, 12)
            }
            4 -> {
                return O(1, 12)
            }
            5 -> {
                return Z(1, 12)
            }
            6  -> return S(1, 12)

            else -> L(1, 12)
        }
    }

    //Essa função é usadada para pintar as partes onde parou uma peça para que possam ser vistas na próxima execução
    fun pintarBord(){

        boardView[pt.pontoA.x][pt.pontoA.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pontoB.x][pt.pontoB.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pontoC.x][pt.pontoC.y]!!.setImageResource(R.drawable.white)
        boardView[pt.pontoD.x][pt.pontoD.y]!!.setImageResource(R.drawable.white)

    }

    fun destroi(linha:Int){
        board[linha] = Array(COLUNA){0}
        for ( i in linha downTo 1){
            Log.i("Entrou","Nessa parte ele deveria descer")
            board[i] = board[i - 1]
        }
    }

    fun verificarLinha(){

        //var linhaAtual = 0
        for (i in 0 until LINHA) {
            var contador = 0
            for (x in 0 until COLUNA) {
                if (board[i][x] == 1) {
                    Log.i("Entrou","Nessa parte ele ver se tem 1 o cont ta em $contador")
                    contador += 1
                    if (contador == 26) {
                        Log.i("Entrou","Nessa parte o contador da $contador e devia destruir")
                        destroi(i)
                        pontuacao += 50
                        textPonto.text = "Score: $pontuacao"
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()



    }

}
