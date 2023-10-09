package com.dewabrata.todolist.apiservice

import android.location.Location
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIServices {

    @GET("ujian/all")
    fun getAllData() : Call<ResponseGetAllData>

    @Multipart
    @POST("ujian/add")
    fun addDataTodoList(@Part("nama") nama:RequestBody ,
                        @Part("alamat") alamat:RequestBody,
                        @Part("jml_out")jml_out: RequestBody,
                      ) : Call<ResponseSuccess>

    @Multipart
    @POST("ujian/update")
    fun updateDataTodoList(@Part("id") id:RequestBody,
                           @Part("nama") nama:RequestBody ,
                           @Part("alamat") alamat:RequestBody,
                           @Part("jml_out")jml_out: RequestBody,
    ) : Call<ResponseSuccess>

    @Multipart
    @POST("ujian/delete")
    fun deleteDataTodoList(@Part("id") id:RequestBody) : Call<ResponseSuccess>


    @GET("ujian/all")
    fun getAllDataByFilter(@Query("filters[0][co][0][fl]") filterField : String,
                           @Query("filters[0][co][0][op]") filterOperator : String,
                           @Query("filters[0][co][0][vl]") filterValue : String

    ) : Call<ResponseGetAllData>


}