package com.cookandroid.happycup.ui.main.view.fragment

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cookandroid.happycup.R
import com.cookandroid.happycup.ui.main.view.activity.MainActivity
import com.cookandroid.happycup.util.Constants
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_my2.*
import org.w3c.dom.Text
import java.nio.file.Files.find

class MyFragment2 : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //return inflater.inflate(R.layout.fragment_my2, container, false)
        val view: View = inflater!!.inflate(R.layout.fragment_my2, container, false)

        val btn1: TextView = view.findViewById(R.id.p1)
        val btn2: TextView = view.findViewById(R.id.p2)
        val btn3: TextView = view.findViewById(R.id.p3)
        val btn4: TextView = view.findViewById(R.id.p4)
        val btn5: TextView = view.findViewById(R.id.p5)
        val btn6: TextView = view.findViewById(R.id.p6)

        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)

        return view
    }

    companion object {
        fun newInstance(): MyFragment2 {
            return MyFragment2()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.p1 -> {
                //startActivity(Intent(requireContext(), product::class.java))
                // Dialog만들기
                val tt : String = p1.text as String
                dialog(tt)
            }
            R.id.p2 -> {
                val tt : String = p2.text as String
                dialog(tt)
            }
            R.id.p3 -> {
                val tt : String = p3.text as String
                dialog(tt)
            }
            R.id.p4 -> {
                val tt : String = p4.text as String
                dialog(tt)
            }
            R.id.p5 -> {
                val tt : String = p5.text as String
                dialog(tt)
            }
            R.id.p6 -> {
                val tt : String = p6.text as String
                dialog(tt)
            }
        }
    }

    fun dialog(string:String){
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.product, null)
        val mBuilder = context?.let {
            AlertDialog.Builder(it)
                .setIcon(R.drawable.cupping)
                .setView(mDialogView)
                .setTitle(string)
                .setMessage("상품을 구매하시겠습니까?")
                .setCancelable(false)
                .setNegativeButton("좋아요", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(context, "like Button Click", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(context, ShopFragment::class.java))
                })
                .setPositiveButton("구매", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(context, "buy Button Click", Toast.LENGTH_SHORT).show()
                })
                .setNeutralButton("취소", DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(context, "cancel Button Click", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(context, PhotoCollectionActivity::class.java))
                })
        }
        mBuilder?.show()
    }
}
