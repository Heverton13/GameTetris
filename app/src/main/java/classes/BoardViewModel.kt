package classes

import androidx.lifecycle.ViewModel

class BoardViewModel: ViewModel(){

    val ROW = 36
    val COL = 22

    var board = Array(ROW) {
        Array(COL) { 0 }
    }

}