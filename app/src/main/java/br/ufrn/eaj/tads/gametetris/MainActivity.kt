package br.ufrn.eaj.tads.gametetris

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.LayoutInflater
import classes.*
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    val LINHA = 36
    val COLUNA = 26
    var running = true
    var speed:Long = 350

    var pt:Peca = getRadomPeca()

    var board = Array(LINHA) {
        Array(COLUNA){0}
    }

    var boardView = Array(LINHA){
        arrayOfNulls<ImageView>(COLUNA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridboard.rowCount = LINHA
        gridboard.columnCount = COLUNA

        // Gerar as paças aleatórias // #Verificar se funcionar quando gerar conflitos
        //pecas = Random.nextInt(0, 6)


        val inflater = LayoutInflater.from(this)

        for (i in 0 until LINHA) {
            for (j in 0 until COLUNA) {
                boardView[i][j] = inflater.inflate(R.layout.inflate_image_view, gridboard, false) as ImageView
                gridboard.addView( boardView[i][j])
            }
        }

        buttonLeft.setOnClickListener {
            pt.moveLeft()
        }

        buttonRight.setOnClickListener {
            if(verificarParadaDireita())
            pt.moveRight()
        }

        buttonRotate.setOnClickListener {
            pt.rotate()
        }

        buttonSpeed.setOnClickListener {
            speed -= 100
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
                                    boardView[i][j]!!.setImageResource(R.drawable.gray)
                                }
                            }
                        }
                    }
                    //move peça atual
                    if(verificarParada()){
                        pt.moveDown()
                        pintarBord()
                    }else{
                        pintarBord()
                        // preenche a board onde a peça parou com 1, para que seja mantido sua posição e visualização com vir a outra peça
                        board[pt.pontoA.x][pt.pontoA.y] = 1
                        board[pt.pontoB.x][pt.pontoB.y] = 1
                        board[pt.pontoC.x][pt.pontoC.y] = 1
                        board[pt.pontoD.x][pt.pontoD.y] = 1
                        pt = getRadomPeca()
                    }
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
}
