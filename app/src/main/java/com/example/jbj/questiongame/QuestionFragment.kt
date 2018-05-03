package com.example.jbj.questiongame

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jbj.questiongame.models.QuestionDatas
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class QuestionFragment : Fragment() ,View.OnClickListener {
    private lateinit var mMainActivity : MainActivity
    var questionDatas: QuestionDatas? = null
    var ans:String=""
    val sPool = Executors.newScheduledThreadPool(1)
    var timer :Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_question, container, false)
        mMainActivity = activity as MainActivity
        initViews(view)
        getQuestion()
        return view
    }

    private fun initViews(view: View) {
        view.ans_A.setOnClickListener(this)
        view.ans_B.setOnClickListener(this)
        view.ans_C.setOnClickListener(this)
        view.ans_D.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ans_A->ans="A"
            R.id.ans_B->ans="B"
            R.id.ans_C->ans="C"
            R.id.ans_D->ans="D"
        }
        check()
    }
    private fun getQuestion() {
        val request = Request.Builder()
                .url("http://127.0.0.1/TestQuestionGame/public/api/getAQuestion")
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                mMainActivity.runOnUiThread {
                    question.setText("無法連接伺服器")
                }
            }

            override fun onResponse(call: Call?, response: Response?) {
                val StatusCode = response?.code()
                val ResMessage = response?.body()?.string()
                mMainActivity.runOnUiThread {
                    if (StatusCode == 200) {
                        questionDatas = Gson().fromJson(ResMessage, QuestionDatas::class.java)
                        layout_question.setBackgroundResource(R.drawable.corners_bg)
                        question.setText("${questionDatas?.questiondata?.question}" )
                        ans_A.setBackgroundResource(R.drawable.corners_bg)
                        ans_A_Text.setText("A.${questionDatas?.questiondata?.answer_a}")
                        ans_B.setBackgroundResource(R.drawable.corners_bg)
                        ans_B_Text.setText("B.${questionDatas?.questiondata?.answer_b}")
                        ans_C.setBackgroundResource(R.drawable.corners_bg)
                        ans_C_Text.setText("C.${questionDatas?.questiondata?.answer_c}")
                        ans_D.setBackgroundResource(R.drawable.corners_bg)
                        ans_D_Text.setText("D.${questionDatas?.questiondata?.answer_d}")
                        startTimer()
                    }else{
                        question.setText("讀取資料失敗")
                    }
                }
            }
        })
    }
    private fun check(){
        if(questionDatas!=null){
            if(ans==questionDatas?.questiondata?.correct){
                mMainActivity.setResultText("答對")
            }else{
                mMainActivity.setResultText("答錯")
            }
            endGame()
        }
    }
    val taskOne = object : TimerTask() {
        override fun run() {
            timer+=1
            mMainActivity.runOnUiThread {
                tv_time.setText("剩餘:" + (6 - timer) + "秒")
            }
            if(timer == 6){
                mMainActivity.setResultText("時間到了")
                endGame()
            }

        }
    }
    private fun startTimer(){
        sPool.scheduleWithFixedDelay(taskOne,0,1000, TimeUnit.MILLISECONDS)
    }
    private fun endGame(){
        sPool.shutdown()
        mMainActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main_container, ResultFragment(), "ResultFragment")
                .commit()
    }
}
