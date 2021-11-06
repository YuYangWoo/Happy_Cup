package com.cookandroid.happycup.ui.main.view.dialog

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cookandroid.happycup.NavigationDirections
import com.cookandroid.happycup.R
import com.cookandroid.happycup.data.singleton.MySharedPreferences
import com.cookandroid.happycup.databinding.DialogExplainBinding
import com.cookandroid.happycup.ui.base.BaseDialogFragment
import com.cookandroid.happycup.ui.main.viewmodel.MainViewModel
import com.cookandroid.happycup.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.*


class ExplainDialog(var kind: String) :
    BaseDialogFragment<DialogExplainBinding>(R.layout.dialog_explain) {
    private val dialog by lazy {
        ProgressDialog(requireContext())
    }

    companion object {
        const val TAG = "ExplainDialog"
    }

    private val viewModel: MainViewModel by sharedViewModel()
    override fun init() {
        super.init()
        img()
        nextScreen()
        btn()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.plasticData.observe(viewLifecycleOwner,
            Observer { t ->
                Log.d(TAG, "initViewModel:${t.toString()} ")
            })
    }

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val extra = intent!!.extras
                val imageBitmap: Bitmap = extra!!.get("data") as Bitmap // bitmap
                val storage: File = requireActivity().cacheDir
                val fileName = "name.jpg"
                val imgFile = File(storage, fileName)

                if (viewModel.kind.value == "plastic") {
                    viewModel.plasticApiCall2(bitmapToFile(imageBitmap, imgFile, fileName))
                        .observe(viewLifecycleOwner, Observer { resource ->
                            when (resource.status) {
                                Resource.Status.SUCCESS -> {
                                    dialog.dismiss()
                                    when (resource.data!!.code()) {
                                        200 -> {
                                            val plasticResponse = resource.data!!.body()
                                            Log.d(TAG, "${plasticResponse.toString()}: ")
                                            if (plasticResponse!!.communication_success && plasticResponse.class_name == "plastic_cup") {
//                                                DecisionDialog(requireContext(), "returnSuccess").show(requireActivity().supportFragmentManager, "DecisionDialog")
                                                findNavController().navigate(
                                                    NavigationDirections.globalDecision(
                                                        "returnSuccess"
                                                    )
                                                )
                                                dismiss()
                                            } else {
                                                Log.d(TAG, "ㄹㄴㅇㄹㄹㄴㄹㅇㄹ: ")
                                                try{
                                                    findNavController().navigate(
                                                        NavigationDirections.globalDecision(
                                                            "returnFail"
                                                        )
                                                    ) 
                                                } catch (e: Exception) {
                                                    Log.d(TAG, "${e.toString()}: ")
                                                }
                                           
//                                                DecisionDialog("returnFail").show(requireActivity().supportFragmentManager, "DecisionDialog")
                                                dismiss()
                                            }
                                        }
                                        else -> {
                                            toast(requireContext(), "${resource.data!!.code()} 에러")
                                        }
                                    }

                                }
                                Resource.Status.LOADING -> {
                                    dialog.show()
                                }
                                Resource.Status.ERROR -> {
                                    toast(
                                        requireContext(),
                                        resource.message + "\n" + resources.getString(R.string.connect_fail)
                                    )
                                    Log.d(
                                        TAG,
                                        "${resource.message + "\n" + resources.getString(R.string.connect_fail)} "
                                    )
                                    dialog.dismiss()
                                }
                            }
                        })
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        var job1 = CoroutineScope(Dispatchers.IO).launch {
                            viewModel.paperApiCall1(bitmapToFile(imageBitmap, imgFile, fileName))
                                .observe(viewLifecycleOwner, Observer { resource ->
                                    when (resource.status) {
                                        Resource.Status.SUCCESS -> {
                                            dialog.dismiss()
                                            val paper = resource.data!!.body()
                                            Log.d(TAG, "통신성공: ${resource.data.body()}")
                                        }
                                        Resource.Status.LOADING -> {
                                            dialog.show()
                                        }
                                        Resource.Status.ERROR -> {
                                            toast(
                                                requireContext(),
                                                resource.message + "\n" + resources.getString(R.string.connect_fail)
                                            )
                                            Log.d(
                                                TAG,
                                                "${resource.message + "\n" + resources.getString(R.string.connect_fail)} "
                                            )
                                            dialog.dismiss()
                                        }
                                    }
                                })
                        }
                        job1.join()
                        var job2 = CoroutineScope(Dispatchers.IO).launch {
                            viewModel.paperApiCall2(bitmapToFile(imageBitmap, imgFile, fileName))
                                .observe(viewLifecycleOwner, Observer { resource ->
                                    when (resource.status) {
                                        Resource.Status.SUCCESS -> {
                                            dialog.dismiss()
                                            val paper = resource.data!!.body()
                                            Log.d(TAG, "통신성공: ${resource.data.body()}")
                                        }
                                        Resource.Status.LOADING -> {
                                            dialog.show()
                                        }
                                        Resource.Status.ERROR -> {
                                            toast(
                                                requireContext(),
                                                resource.message + "\n" + resources.getString(R.string.connect_fail)
                                            )
                                            Log.d(
                                                TAG,
                                                "${resource.message + "\n" + resources.getString(R.string.connect_fail)} "
                                            )
                                            dialog.dismiss()
                                        }
                                    }
                                })
                        }
                        job2.join()

                    }

                }

            }
        }

    private fun bitmapToFile(imageBitmap: Bitmap, imgFile: File, fileName: String): RequestBody {
        try {
            imgFile.createNewFile()
            var out = FileOutputStream(imgFile)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var img = File(requireActivity().cacheDir, fileName)
        Log.d("TAG", ": ${img}.")
        var requestBody: RequestBody? = null
        try {
            var inputStream = FileInputStream(img)
            var buf = ByteArray(inputStream.available())
            while (inputStream.read(buf) != -1)
                requestBody =
                    RequestBody.create("application/octet-stream".toMediaTypeOrNull(), buf)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return requestBody!!
    }

    private fun btn() {
        binding.chkBox.setOnClickListener {
            MySharedPreferences.setExplain(requireContext(), true)
        }
    }

    private fun img() {
        if (kind == "종이") {
            binding.imgContent.setImageResource(R.drawable.img_paper)
        } else {
            binding.imgContent.setImageResource(R.drawable.img_plastic)
        }
    }


    private fun nextScreen() {
        CoroutineScope(Dispatchers.Main).launch {
            var intent = Intent().apply {
                action = MediaStore.ACTION_IMAGE_CAPTURE
            }
            if (MySharedPreferences.getExplain(requireContext())) {
                startForResult.launch(intent)
            } else {
                delay(2000L)
                startForResult.launch(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(this@ExplainDialog, 0.85f, 0.85f)
    }
}