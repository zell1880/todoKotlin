package com.example.android.todo.api

import retrofit2.Call
import retrofit2.http.*

interface TodoService {
    @GET("/todos")
    fun getTodoList(): Call<List<TodoResponse>>

    @GET("todos/archived")
    fun getArchivedTodo():Call<List<TodoResponse>>

    @POST("/todos")
    @FormUrlEncoded
    fun addTodo(@Field("title")title:String,
                @Field("content")content:String):Call<TodoResponse>

    @PUT("/todos/{id}")
    @FormUrlEncoded
    fun editTodo(@Path("id") id:String,
                @Field("title")title:String,
                @Field("content")content:String,
                @Field("done")done:Boolean,
                @Field("archived")archived:Boolean):Call<TodoResponse>
    @PUT("/todos/{id}")
    @FormUrlEncoded
    fun archiveTodo(@Path("id") id:String,
                 @Field("archived")archived:Boolean):Call<TodoResponse>

    @PUT("/todos/{id}")
    @FormUrlEncoded
    fun doneTodo(@Path("id") id:String,
                 @Field("done")done:Boolean):Call<TodoResponse>

    @DELETE("/todos/{id}")
    fun deleteTodo(@Path("id") id: String?):Call<TodoResponse>


}