package com.example.jbj.questiongame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var ResultText:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_main_container, StartFragment(), "StartFragment")
                .commit()
//        getQuestion()
    }
    fun setResultText(result:String){
        ResultText=result;
    }
    fun  getResultText():String{
        return ResultText;
    }
}
