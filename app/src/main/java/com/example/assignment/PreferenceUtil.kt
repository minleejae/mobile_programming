package com.example.assignment

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

//프리퍼런스를 전역으로 관리하기 위한 클래스
class PreferenceUtil(context: Context) {
    private var curString = "[]"
    private var curJsonArray = JSONArray()
    private val preferences: SharedPreferences = context.getSharedPreferences("prefs_users", Context.MODE_PRIVATE)

    fun getString(key: String):String{
        return preferences.getString(key,"[]").toString()
    }

    private fun setString(key: String, defValue: String){
        curString = defValue;
        preferences.edit().putString(key, defValue).apply()
    }

    fun getMember(key:String): JSONArray{
        val originString = getString(key)
        val jsonArray = JSONArray(originString)
        curJsonArray = jsonArray

        return curJsonArray
    }


    fun addMember(key:String, obj:JSONObject){
        //초기값이 없다면 빈 배열로 불러온다
        val originString = getString(key)
        val jsonArray = JSONArray(originString)
        val result = jsonArray.put(obj)
        curJsonArray = result
        setString(key, result.toString())
    }
}