package com.example.myapplication1.main

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.provider.MediaStore
import android.system.Os.close
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.CursorUtil.getColumnIndex
import com.example.myapplication1.R
import com.example.myapplication1.common.ActivityController
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity2 : AppCompatActivity(), View.OnClickListener {

    private var backButton: Button? = null
    private var callButton: Button? = null
    private var activityListView: RecyclerView? = null

    private val myActivities = ArrayList<MyActivity>()
    private val mContacts = ArrayList<MyContact>()
    private var contactsAdapter = MyContactsAdapter(mContacts)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main2)

        backButton = findViewById(R.id.backButton)
        backButton?.setOnClickListener{
            var anime = ObjectAnimator.ofFloat()
        }

        callButton = findViewById(R.id.callButton)
        callButton?.setOnClickListener(this)

        test_button.setOnClickListener(this)

        activityListView = findViewById(R.id.activity_list_view)
        initActivityListView()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        activityListView?.layoutManager = layoutManager
        activityListView?.adapter = MyActivityAdapter(myActivities)

        val contactsLayoutManager = LinearLayoutManager(this)
        contactsLayoutManager.orientation = LinearLayoutManager.VERTICAL
        contactsList.layoutManager = contactsLayoutManager
        contactsList.adapter = contactsAdapter

        ActivityController.addActivity(this)

        Handler(Looper.getMainLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.removeActivity(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){
            R.id.backButton -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.callButton -> {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(this,
                            arrayOf(Manifest.permission.CALL_PHONE),1)
                } else{
                    call()
                }
            }

            R.id.test_button -> {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_CONTACTS),2)
                } else{
                    readContacts()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        when(requestCode){
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call()
                } else{
                    Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show()
                }
            }

            2 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts()
                } else{
                    Toast.makeText(this, "取消授权", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initActivityListView() { //初始化activity列表
        with(myActivities){
            add(
                MyActivity(
                    "主 页",
                    R.drawable.home_foreground
                )
            )
            add(
                MyActivity(
                    "新 闻",
                    R.drawable.news_foreground
                )
            )
            add(
                MyActivity(
                    "下 载",
                    R.drawable.downloads_foreground
                )
            )
            add(
                MyActivity(
                    "设 置",
                    R.drawable.settings_foreground
                )
            )
            add(
                MyActivity(
                    "客 服",
                    R.drawable.customer_service_foreground
                )
            )
            add(
                MyActivity(
                    "个 人",
                    R.drawable.personal_foreground
                )
            )
        }
    }

    fun readContacts(){ //查询联系人
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)?.apply {
            while (moveToNext()){
                val name = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                mContacts.add(MyContact(name, number))
                contactsAdapter.notifyDataSetChanged()
            }
            close()
        }
    }

    fun call(){ //打电话
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:13671092791")
            startActivity(intent)
        }catch (e: SecurityException){
            e.printStackTrace()
        }
    }
}