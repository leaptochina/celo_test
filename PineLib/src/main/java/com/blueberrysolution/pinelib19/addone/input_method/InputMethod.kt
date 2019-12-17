package com.blueberrysolution.pinelib19.addone.input_method

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.blueberrysolution.pinelib19.activity.A
import androidx.core.content.ContextCompat.getSystemService
import java.lang.Exception
import androidx.core.content.ContextCompat.getSystemService




object InputMethod{
    fun isOpen(): Boolean{
        val imm = A.c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        return imm!!.isActive();
    }

    fun hide(view: View? = null){
        try{
            if (view == null){
                (A.c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    A.a().currentFocus!!.windowToken, InputMethodManager.RESULT_HIDDEN);
            }else{
                val imm = A.c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
            }

        }
        catch (e: Exception ){

        }

    }

    fun show(view: View){

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();

        val imm = A.c().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(view, InputMethodManager.SHOW_FORCED)


    }
}