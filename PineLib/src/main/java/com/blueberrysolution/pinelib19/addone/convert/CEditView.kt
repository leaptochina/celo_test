package com.blueberrysolution.pinelib19.addone.convert

import android.text.Editable
import android.widget.EditText

fun EditText.setText(value: String){

    val e = Editable.Factory.getInstance().newEditable(value)
    this.text = e;
}