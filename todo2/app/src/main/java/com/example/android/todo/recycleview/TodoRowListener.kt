package com.example.android.todo.recycleview

import com.example.android.todo.api.TodoResponse

interface TodoRowListener {
    fun modifyItemState(itemObjectId:String?, isDone:Boolean)
    fun onItemDelete(itemObjectId:String?)
    fun onItemSelected(itemObject: TodoResponse)
}