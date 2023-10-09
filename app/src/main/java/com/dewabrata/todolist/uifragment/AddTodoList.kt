package com.dewabrata.todolist.uifragment

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Switch
import androidx.core.graphics.drawable.toBitmap
import com.dewabrata.todolist.R
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import com.dewabrata.todolist.apiservice.model.UjianItem

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTodoList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTodoList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: UjianItem? = null
    lateinit var txtNama :EditText
    lateinit var txtAlamat :EditText
    lateinit var txtJml :EditText

    lateinit var btnSend : Button



    lateinit var progressBar: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2,UjianItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtNama = view.findViewById(R.id.editNama)
        txtAlamat = view.findViewById(R.id.editAlamat)
        txtJml = view.findViewById(R.id.editjml)
        btnSend = view.findViewById(R.id.btnSend)


        progressBar = view.findViewById(R.id.progressBar2)




        if(param1 == "add"){

            btnSend.setOnClickListener(View.OnClickListener {
                addDataTodoList(UjianItem(null,
                    txtNama.text.toString(),
                    txtAlamat.text.toString(),
                    txtJml.text.toString(),

                   ))

            })

        }else{
            txtNama.setText( param2?.nama)
            txtAlamat.setText(param2?.alamat)
            txtJml.setText(param2?.jmlOut)


            btnSend.setOnClickListener {

                updateDataTodoList(UjianItem(param2?.id,txtNama.text.toString(),txtAlamat.text.toString(),txtJml.text.toString()))
            }
        }
}
        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTodoList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: UjianItem) =
            AddTodoList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
    }

    fun addDataTodoList(data : UjianItem){


        val client = APIConfig.getApiService()
            .addDataTodoList(toRequestBody(data.nama.toString()),
                toRequestBody(data.alamat.toString()),
                toRequestBody(data.jmlOut.toString()),



            )

        showProgressBar(true)
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    showProgressBar(false)
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun updateDataTodoList(data : UjianItem){
        val client = APIConfig.getApiService()
            .updateDataTodoList(toRequestBody(data.id.toString()),
                toRequestBody(data.nama.toString()),
                toRequestBody(data.alamat.toString()),
                toRequestBody(data.jmlOut.toString()))





        showProgressBar(true)
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                    showProgressBar(false)
                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }
    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun showProgressBar(flag:Boolean){
        if (flag){
            progressBar.visibility = View.VISIBLE
            progressBar.animate()
        }else{
            progressBar.visibility = View.GONE

        }
    }

    fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
           takePictureIntent.resolveActivity(requireActivity().packageManager)
            startActivityForResult(takePictureIntent, 1)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//         if(requestCode==1 && resultCode == Activity.RESULT_OK){
//             val imageBitmap = data?.extras?.get("data") as Bitmap
//             image.setImageBitmap(imageBitmap)
//             bitmap = imageBitmap
//         }
//    }

//    fun createImageRequestBody(bitmap:Bitmap?):MultipartBody.Part{
//
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap?.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream)
//        val imageBytes = byteArrayOutputStream.toByteArray()
//
//        val requestBody = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
//
//        return MultipartBody.Part.createFormData("image", System.currentTimeMillis().toString()+"image.jpg", requestBody)
//    }

}