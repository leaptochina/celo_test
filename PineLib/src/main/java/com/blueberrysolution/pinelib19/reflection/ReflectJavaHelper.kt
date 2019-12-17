package com.blueberrysolution.pinelib19.reflection

import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties

object ReflectJavaHelper {

    fun getFields(className:  Class<*>): Array<Field> {
        var fields = className.fields;
        return fields
    }

    fun getField(className:  Class<*>, key: String): Field? {

       return  className.getDeclaredField(key);

    }


    fun setValue(className: Class<*>, instance: Any, key: String, value: Any?): Boolean{

        var field = className.getDeclaredField(key);
        if (field != null){
            field.isAccessible = true;
            field.set(instance, value)
            return  true
        }
        else{
            //G.d("Setting Value Error, Please check " + className.simpleName + " Model has " + key + "?")
            return false
        }




    }
}