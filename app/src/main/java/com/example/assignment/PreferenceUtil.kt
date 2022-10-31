package com.example.assignment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
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

    private fun setString(key: String, defValue: String):Boolean{
        curString = defValue;
        return preferences.edit().putString(key, defValue).commit()
    }

    fun getMember(key:String): JSONArray{
        val originString = getString(key)
        val jsonArray = JSONArray(originString)
        curJsonArray = jsonArray

        return curJsonArray
    }


    fun addMember(context: Context ,key:String, obj:JSONObject): Boolean{
        //초기값이 없다면 빈 배열로 불러온다
        val originString = getString(key)
        val jsonArray = JSONArray(originString)
        val result = jsonArray.put(obj)
        curJsonArray = result
        val dataSaveResult = setString(key, result.toString())

        if(dataSaveResult){
            Toast.makeText(context, "회원 가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
            return true
        }else{
            Toast.makeText(context, "회원 가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}