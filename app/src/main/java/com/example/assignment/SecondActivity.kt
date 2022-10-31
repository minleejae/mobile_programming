package com.example.assignment

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.assignment.databinding.ActivitySecondBinding
import org.json.JSONArray
import org.json.JSONObject

class SecondActivity : AppCompatActivity() {
    // 뷰바인딩을 이용하기 위해 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivitySecondBinding? = null
    private val binding get() = mBinding!!

    //preference
    companion object {
        lateinit var preferences: PreferenceUtil
    }

    private var userArray: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)

        mBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //프리퍼런스 정보 확인하기 위해 유저 데이터 불러오기
        userArray = preferences.getMember("users")

        // id 확인을 위한 정규식 소문자, 숫자 6~13글자 아이디
        val regexId = Regex("[a-z0-9]{6,13}\$")
        // 특수문자, 문자, 숫자를 포함한 6~13글자 비밀번호
        val regexPassword =
            Regex("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{6,13}\$")
        // 전화번호 확인을 위한 정규식 010-1234-5678
        val regexPhone = Regex("\\d{3}-\\d{4}-\\d{4}\$")
        // 이름 확인을 위한 영어, 한글, 띄어쓰기 허용 정규식
        val regexName = Regex("^[\\s가-힣a-zA-Z]{2,20}\$")
        // 주소 확인을 위한 영어, 한글, 숫자, 띄어쓰기 허용 정규식
        val regexAddr = Regex("^[\\s0-9가-힣a-zA-Z]{2,50}\$")

        //회원가입 버튼 클릭
        binding.btnSignup.setOnClickListener {
            val signUpId = binding.etId.text.toString()
            val signUpPassword = binding.etPwd.text.toString()
            val signUpName = binding.etName.text.toString().trim()
            val signUpPhone = binding.etPhone.text.toString()
            val signUpAddr = binding.etAddr.text.toString().trim()
            val isInfoAgree = binding.btnInfoAgree.isChecked

            //id 확인
            if (!signUpId.matches(regexId)) {
                Toast.makeText(this, "아이디는 알파벳 소문자나 숫자로 6~13글자여야 합니다.", Toast.LENGTH_SHORT).show()
                binding.etId.requestFocus()
                return@setOnClickListener
            }

            //비밀번호 확인
            if (!signUpPassword.matches(regexPassword)) {
                Toast.makeText(
                    this,
                    "비밀번호는 알파벳, 숫자, 특수문자(! @ # $ % ^ & *)를 포함해 6~13글자여야 합니다.",
                    Toast.LENGTH_SHORT
                ).show()
                binding.etPwd.requestFocus()
                return@setOnClickListener
            }

            //이름 확인
            if (signUpName.isEmpty()) {
                Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.etName.requestFocus()
                return@setOnClickListener
            }
            if (!signUpName.matches(regexName)) {
                Toast.makeText(this, "이름 입력은 한글이나 영어만 가능합니다.", Toast.LENGTH_SHORT).show()
                binding.etName.requestFocus()
                return@setOnClickListener
            }


            //전화번호 확인
            if (!signUpPhone.matches(regexPhone)) {
                Toast.makeText(this, "전화번호를 010-1234-5678 형식으로 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.etPhone.requestFocus()
                return@setOnClickListener
            }

            //주소 확인
            if (signUpAddr.isEmpty()) {
                Toast.makeText(this, "주소를 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.etAddr.requestFocus()
                return@setOnClickListener
            }
            if (!signUpAddr.matches(regexAddr)) {
                Toast.makeText(this, "주소 입력은 한글이나 영어, 숫자만 가능합니다.", Toast.LENGTH_SHORT).show()
                binding.etAddr.requestFocus()
                return@setOnClickListener
            }


            //개인정보 동의
            if (!isInfoAgree) {
                Toast.makeText(this, "개인정보 제공 동의 후 회원가입이 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //아이디가 프리퍼런스에 이미 존재하는 체크
            for (i in 0 until userArray!!.length()) {
                val user = userArray!!.getJSONObject(i)

                if (user["id"] == signUpId) {
                    Toast.makeText(this, "이미 존재하는 아이디입니다. 아이디를 변경해주세요", Toast.LENGTH_SHORT).show()
                    binding.etId.requestFocus()
                    return@setOnClickListener
                }
            }

            //현재 회원가입이 유효한 경우 프리퍼런스에 유저 정보 추가
            val json = JSONObject()
            json.put("id", signUpId)
            json.put("password", signUpPassword)
            json.put("name", signUpName)
            json.put("phone", signUpPhone)
            json.put("address", signUpAddr)
            json.put("info_agree", isInfoAgree.toString())
            val res = preferences.addMember(this, "users", json)

            //저장에 실패할 경우
            if(!res) return@setOnClickListener

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}