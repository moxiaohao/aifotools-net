package io.aifo.example

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.foryou.net.filter.data.RespData
import com.foryou.net.http.HttpCreator
import com.foryou.net.live.HttpCall
import com.foryou.net.live.Resource
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.HashMap

/**
 * Description:
 * Created by shenlei
 * Date:2019-06-21 15:09
 * Email:shenlei@foryou56.com
 */
class SecondActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_hello.setOnClickListener {

            request()?.observe(this@SecondActivity, Observer {

            })
        }
    }

    fun request(): LiveData<Resource<SingleEntity>>? {
        return object : HttpCall<SingleEntity>() {
            override fun liveMethod(): LiveData<RespData<SingleEntity>>? {
                val params = HashMap<String, Any>()
                return HttpCreator.getService(CommonService::class.java).update(params)
            }
        }.asLiveData()
    }




}