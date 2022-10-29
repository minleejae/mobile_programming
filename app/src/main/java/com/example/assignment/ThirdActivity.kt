package com.example.assignment

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    // 뷰바인딩을 이용하기 위해 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityThirdBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //회원 정보 보기 버튼 클릭 시
        binding.btnUserInfo.setOnClickListener {
            //로그인 하지 않은 경우
            if(Data.curLoginId==null){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("회원 가입을 하셨나요?")
                    .setPositiveButton("네",
                        DialogInterface.OnClickListener { dialog, id ->
                            // 로그인 할지 여부를 물어보기
                            val loginBuilder = AlertDialog.Builder(this)
                                .setMessage("로그인을 하시겠습니까?")
                                .setPositiveButton("네",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // 회원 가입 액티비티 실행하고 현재 액티비티를 닫는다.
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                        return@OnClickListener
                                    })
                                .setNegativeButton("아니오",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        return@OnClickListener
                                    })
                            loginBuilder.create().show()
                        })
                    .setNegativeButton("아니오",
                        DialogInterface.OnClickListener { dialog, id ->
                            // 회원 가입을 할 지 여부를 물어보기
                            val signUpBuilder = AlertDialog.Builder(this)
                                .setMessage("회원 가입을 하시겠습니까?")
                                .setPositiveButton("네",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        // 회원 가입 액티비티 실행하고 현재 액티비티를 닫는다.
                                        startActivity(Intent(this, SecondActivity::class.java))
                                        finish()
                                        return@OnClickListener
                                    })
                                .setNegativeButton("아니오",
                                    DialogInterface.OnClickListener { dialog, id ->
                                        return@OnClickListener
                                    })
                            signUpBuilder.create().show()
                        })
                // Create the AlertDialog object and return it
                builder.create()
                builder.show()

                return@setOnClickListener
            }

            //로그인 한 경우 유저 정보 보여주기
            AlertDialog.Builder(this)
                .setTitle("My Info")
                .setMessage("아이디 :${Data.curLoginId}\n" +
                            "비밀번호 :${Data.password}\n" +
                            "이름 :${Data.name}\n" +
                            "주소 :${Data.address}\n"+
                            "개인 정보 활용 동의 : ${Data.infoAgree}")
                .show()
        }
    }
}