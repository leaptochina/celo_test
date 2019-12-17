package com.blueberrysolution.pinelib19.debug.big_window.function

import kotlin.reflect.KFunction0

object DebugFunctions {
    var  beans = ArrayList<FunctionBean>();

    fun add(key: String, func: KFunction0<Unit>){
        var fb = FunctionBean(key, func);
        beans.add(0, fb);
    }

    fun clear() {
        beans.clear();
    }
}



