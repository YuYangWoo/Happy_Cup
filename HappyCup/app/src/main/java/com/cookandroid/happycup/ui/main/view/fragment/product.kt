package com.cookandroid.happycup.ui.main.view.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.happycup.R

//, View.OnClickListener
class product : AppCompatActivity() {

    var button : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Dialog만들기
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.product, null)
        val mBuilder = AlertDialog.Builder(this)
            .setIcon(R.drawable.cupping)
            .setView(mDialogView)
            .setTitle("상품 구매창 타이틀")
            .setMessage("내용")
            .setCancelable(false)
            .setNegativeButton("좋아요",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "like Button Click", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ShopFragment::class.java))
            })
            .setPositiveButton("구매",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "buy Button Click", Toast.LENGTH_SHORT).show()
            })
            .setNeutralButton("취소",DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(this, "buy Button Click", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, PhotoCollectionActivity::class.java))
            })
        mBuilder.show()

        //button = findViewById(R.id.closeButton)
        //button?.setOnClickListener(this)
    }

    fun dialog(view: View) {
        AlertDialog.Builder(view.context).apply {
            setTitle("알림창 제목")
            setMessage("메세지입니다. 메세지입니다. 메세지입니다. 메세지입니다.")
            setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(view.context, "OK Button Click", Toast.LENGTH_SHORT).show()
            })
            setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(view.context, "Cancel Button Click", Toast.LENGTH_SHORT).show()
            })
            show()
        }
    }


}