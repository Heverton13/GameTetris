package classes

import br.ufrn.eaj.tads.gametetris.R

class T(x:Int, y:Int) : Peca(x,y)  {


    var check = true

    var color : Int = R.drawable.mageta


    init {
        pontoB = Ponto(x-1,y)
        pontoC = Ponto(x,y-1)
        pontoD = Ponto(x,y+1)
    }
    override fun moveDown() {
        pontoA.moveDown()
        pontoB.moveDown()
        pontoC.moveDown()
        pontoD.moveDown()
    }

    override fun moveLeft() {
        pontoA.moveLeft()
        pontoB.moveLeft()
        pontoC.moveLeft()
        pontoD.moveLeft()
    }

    override fun moveRight() {
        pontoA.moveRight()
        pontoB.moveRight()
        pontoC.moveRight()
        pontoD.moveRight()

    }

    override fun rotate() {

        if(check == true) {

            pontoB.x += 1
            pontoB.y += 1

            pontoC.x -= 1
            pontoC.y += 1

            pontoD.x += 1
            pontoD.y -= 1

            check = false

        }else{

            pontoB.x -= 1
            pontoB.y -= 1

            pontoC.x += 1
            pontoC.y -= 1

            pontoD.x -= 1
            pontoD.y += 1

            check = true
        }
    }

    override fun getColorPiece(): Int {
        return color
    }

    override fun setColorPiece(colorRecebida: Int) {
        color = colorRecebida
    }

}