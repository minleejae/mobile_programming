package com.example.assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.assignment.databinding.ActivityMainBinding
import org.json.JSONArray

//로그인 화면 액티비티
class MainActivity : AppCompatActivity() {
    // 뷰바인딩을 이용하기 위해 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private var userArray: JSONArray? = null

    //preference
    companion object{
        lateinit var preferences: PreferenceUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이미 로그인한 경우 바로 상품보기 페이지로 이동한다
        if(Data.curLoginId!= null){
            startActivity(Intent(this, ThirdActivity::class.java))
            finish()
            return
        }


        //회원가입 버튼을 누르면 회원가입 액티비티 실행
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
            finish()
        }

        //상품 보기 버튼을 누르면 상품 보기 액티비티 실행
        binding.btnItems.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
            finish()
        }

        //로그인 버튼 누르면
        binding.btnLogin.setOnClickListener {
            //로그인 하기 위해 유저 데이터 불러오기
            userArray = preferences.getMember("users")

            val curId = binding.etId.text.toString()
            val curPwd = binding.etPwd.text.toString()

            if(curId.isEmpty()){
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.etId.requestFocus()
                return@setOnClickListener
            }

            if(curPwd.isEmpty()){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                binding.etPwd.requestFocus()
                return@setOnClickListener
            }

            //프리퍼런스에 존재하는 데이터와 로그인하는 아이디 비밀번호 체크
            for(i in 0 until userArray!!.length()){
                val user = userArray!!.getJSONObject(i)
                //로그인 성공
                if(user["id"]==curId && user["password"]==curPwd){
                    Data.curLoginId = curId
                    Data.password = user["password"].toString()
                    Data.name = user["name"].toString()
                    Data.phone = user["phone"].toString()
                    Data.address = user["address"].toString()
                    Data.infoAgree = user["info_agree"].toString()
                    startActivity(Intent(this, ThirdActivity::class.java))
                    finish()
                    return@setOnClickListener
                }
            }
            Toast.makeText(this, "아이디와 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}