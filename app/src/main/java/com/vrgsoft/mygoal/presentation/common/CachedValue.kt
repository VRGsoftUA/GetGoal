package com.vrgsoft.mygoal.presentation.common

import android.annotation.SuppressLint
import android.content.SharedPreferences

class CachedValue<T>(val name: String, private var value: T?, private val defValue: T?, private val type: Class<*>) {

    private var sp: SharedPreferences? = null
    private var loaded = false

    constructor(name: String, type: Class<*>) : this(name, null, null, type) {}

    constructor(name: String, defValue: T, type: Class<*>) : this(name, null, defValue, type) {}

    init {
        this.sp = sharedPref
        this.loaded = value != null
    }

    fun setValue(value: T) {
        synchronized(lock) {
            loaded = true
            write(value)
        }
    }

    fun getValue(): T? {
        synchronized(lock) {
            if (!loaded) {
                this.value = load()
                loaded = true
            }
            return this.value
        }
    }

    private fun write(value: T) {
        val editor = sp!!.edit()

        if (value is String) {

            editor.putString(name, value)

        } else if (value is Int) {

            editor.putInt(name, value)

        } else if (value is Float) {

            editor.putFloat(name, value)

        } else if (value is Long) {

            editor.putLong(name, value)

        } else if (value is Boolean) {

            editor.putBoolean(name, value)

        }

        editor.apply()
    }

    @SuppressLint("unchecked")
    private fun load(): T? {

        if (type == String::class.java) {

            return sp!!.getString(name, defValue as String?) as T

        } else if (type == Int::class.java) {

            return Integer.valueOf(sp!!.getInt(name, if (defValue != null) (defValue as Int?)!! else 0)) as T

        } else if (type == Float::class.java) {

            return java.lang.Float.valueOf(sp!!.getFloat(name, if (defValue != null) (defValue as Float?)!! else 0F)) as T

        } else if (type == Long::class.java) {

            return java.lang.Long.valueOf(sp!!.getLong(name, if (defValue != null) (defValue as Long?)!! else 0)) as T

        } else if (type == Boolean::class.java) {

            return java.lang.Boolean.valueOf(sp!!.getBoolean(name, if (defValue != null) (defValue as Boolean?)!! else false)) as T

        }

        return null
    }

    fun delete() {
        synchronized(lock) {
            sp!!.edit().remove(name).commit()
            clear()
        }
    }

    fun setSharedPreferences(sp: SharedPreferences) {
        this.sp = sp
    }

    fun clear() {
        synchronized(lock) {
            loaded = false
            this.value = null
        }
    }

    companion object {

        private val lock = Any()

        private var sharedPref: SharedPreferences? = null

        fun initialize(sp: SharedPreferences) {
            CachedValue.sharedPref = sp
        }
    }

}