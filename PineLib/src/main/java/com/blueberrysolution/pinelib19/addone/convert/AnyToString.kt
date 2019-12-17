package com.blueberrysolution.pinelib19.addone.convert

object AnyToString{
    
    fun toString(any: Any?): String{
        when (any) {
            null -> return "null"
            is String -> return any
            is Int -> return any.toString()
            is Double -> return any.toString()
            is Float -> return any.toString()
            else -> return ""
        };
    }
}