package com.example.jbj.questiongame

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_start.view.*

class StartFragment : Fragment() ,View.OnClickListener{
    private lateinit var mMainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_start, container, false)
        mMainActivity = activity as MainActivity
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        view.tv_start.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if(v?.id==R.id.tv_start){
            mMainActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_main_container, QuestionFragment(), "QuestionFragment")
                    .commit()
        }
    }

}
