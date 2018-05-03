package com.example.jbj.questiongame


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_result.view.*


class ResultFragment : Fragment() ,View.OnClickListener{
    private lateinit var mMainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_result, container, false)
        mMainActivity = activity as MainActivity

        initViews(view)
        return view
    }
    private fun initViews(view: View) {
        view.result.setText(mMainActivity.getResultText())
        view.again.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v?.id==R.id.again){
            mMainActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fl_main_container, QuestionFragment(), "QuestionFragment")
                    .commit()
        }
    }

}
