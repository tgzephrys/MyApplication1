package com.example.myapplication1.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import com.example.myapplication1.common.mUtils.MyIterator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var topButton:Button? = null
    var topTextView:TextView? = null
    var robotCat:ImageView? = null
    var backTime: Long = 0
    var searching = false

    var circleIterator = MyIterator( arrayListOf(
        R.drawable.right,
        R.drawable.left
    ) )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topButton = findViewById(R.id.topButton)
        topButton?.setOnClickListener(this)
        topTextView = findViewById(R.id.top_textView)
        topTextView?.setOnClickListener(this)
        robotCat = findViewById(R.id.RobotCat)
        robotCat?.setOnClickListener(this)
        search.setOnClickListener(this)

        ActivityController.addActivity(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add_item -> Toast.makeText(this, "You click an item",
                    Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You click an item",
                    Toast.LENGTH_SHORT).show()
            R.id.quit_item -> {
                ActivityController.quitApplication()
                    android.os.Process.killProcess(android.os.Process.myPid())}
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.topButton ->{
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            R.id.RobotCat ->{
                robotCat?.setImageResource(circleIterator.circle())
            }
            R.id.search -> {
                webView.visibility = View.VISIBLE
                webView.settings.javaScriptEnabled = true
                webView.webViewClient = WebViewClient()
                webView.loadUrl(when(content.text.toString()){
                                    "" -> "https://www.baidu.com"
                                    else -> "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=${content.text}"
                                }
                        )
                searching = true
            }
        }
    }

    override fun onBackPressed() {
//        AlertDialog.Builder(this).apply {
//            setTitle("是否退出程序")
//            setMessage("点击取消返回")
//            setCancelable(false)
//            setPositiveButton("退出"){
//                    _, _ ->
//                ActivityController.quitApplication()
//            }
//            setNegativeButton("取消"){
//                    _, _ ->
//            }
//            show()
//        }


        if (searching){
            webView.visibility = View.INVISIBLE
            return
        }

        val toast = Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT)
        if(backTime == 0.toLong()  || (System.currentTimeMillis() - backTime > 1000 )){
            toast.show()
            backTime = System.currentTimeMillis()
        }else{
            ActivityController.quitApplication()
        }

    }

}