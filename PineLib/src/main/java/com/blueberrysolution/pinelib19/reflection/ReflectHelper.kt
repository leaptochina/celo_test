package com.blueberrysolution.pinelib19.reflection

import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.functions
import kotlin.reflect.full.memberProperties


object ReflectHelper {

    fun nameToClass(className: String): KClass<*>{
        val class1 = Class.forName(className)
        return class1.kotlin;
    }

    fun classToInstance(className:  KClass<*>): Any{
        return className.java.newInstance()
        //val person = cl as Person
    }

    fun getConstructor(className:  KClass<*>): Array<Constructor<*>> {
        var constructors = className.java.getConstructors();
        return constructors;
    }

    fun getFields(className:  KClass<*>): Collection<KMutableProperty1<Any, Any?>> {
        var fields = className.memberProperties;
        return fields as Collection<KMutableProperty1<Any, Any?>>;
    }

    fun getField(className:  KClass<*>, key: String): KMutableProperty1<Any, Any?>? {

        var fields = getFields(className) as ArrayList
        fields.map {
            if (it.name.equals(key)){
                return it;
            }
        }
        return null;
    }

    fun getMethods(className:  KClass<*>): Collection<KFunction<*>> {
        var methods = className.functions;
        return methods;
    }

    fun getMethod(className:  KClass<*>, funName: String, vararg parameterTypes: Class<*>): KFunction<*>? {
        var methods = getMethods(className);
        methods.map { method ->
            if (method.name.equals(funName)){
                return method;
            }


        }

        return null;
    }

    fun callMethod(method: Method, instance: Any, vararg parameterTypes: Any): Any {
        return method.invoke(instance, *parameterTypes)

    }

    // 获取List<String> 中的String 能不能用不知道
    fun getListStar(classStar: KClass<List<String>>): Class<*> {
        // 如果是List类型，得到其Generic的类型
        val genericType = classStar.typeParameters

        val pt = genericType as ParameterizedType
        //得到泛型里的class类型对象
        val genericClazz = pt!!.getActualTypeArguments()[0] as Class<*>
        return  genericClazz;
    }

    fun setValue(className: KClass<*>, instance: Any, key: String, value: Any?): Boolean{

        var field: KMutableProperty1<Any, Any?>? = getField(className, key);
        if (field != null){
            field.set(instance, value)
            return  true
        }
        else{
            //G.d("Setting Value Error, Please check " + className.simpleName + " Model has " + key + "?")
            return false
        }




    }

    fun getValue(className: KClass<*>, instance: Any, key: String): Any?{
        var field: KMutableProperty1<Any, Any?>? = getField(className, key);

        if (field == null){
            return null;
        }
        return field!!.get(instance)!!

    }

}


