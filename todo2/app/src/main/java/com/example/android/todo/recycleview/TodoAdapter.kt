package com.example.android.todo.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todo.R
import com.example.android.todo.api.TodoResponse

class TodoAdapter(private val context: Context, private val dataList: List<TodoResponse> ):RecyclerView.Adapter<TodoAdapter.ViewHolder>(){
   private var rowListener: TodoRowListener = context as TodoRowListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           LayoutInflater.from(context).inflate(
               R.layout.list_item,
               parent,
               false
           )
       )

   }
    override fun getItemCount(): Int {
        return dataList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position:Int) {
       val dataModel=dataList.get(position)
        holder.titleTextView.text=dataModel.title
        holder.doneCheckBox.isChecked = dataModel.done!!
        holder.doneCheckBox.setOnClickListener{
            rowListener.modifyItemState(dataModel.id, dataModel.done!!)
        }
        holder.deleteButton.setOnClickListener{
            rowListener.onItemDelete(dataModel.id)
        }
        holder.titleTextView.setOnClickListener{
            rowListener.onItemSelected(dataModel)
        }

    }


    class ViewHolder(itemLayoutView:View): RecyclerView.ViewHolder(itemLayoutView){
        var titleTextView:TextView = itemLayoutView.findViewById(R.id.todo_title)
        var doneCheckBox:CheckBox = itemLayoutView.findViewById(R.id.is_done)
        var deleteButton:ImageButton = itemLayoutView.findViewById(R.id.delete_button)

    }



}