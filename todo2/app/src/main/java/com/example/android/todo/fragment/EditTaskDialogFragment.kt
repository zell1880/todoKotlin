package com.example.android.todo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.android.todo.R
import com.example.android.todo.api.TodoResponse

class EditTaskDialogFragment:DialogFragment() {
    interface EditTaskDialogListener{
        fun onEditDialogPositiveClick(dialog: DialogFragment, todo: TodoResponse, newTitle:String, newContent:String)
        fun onEditDialogNegativeClick(dialog: DialogFragment)
        fun onEditDialogArchiveClick(dialog: DialogFragment,todo: TodoResponse)
    }

    var editTaskDialogListener: EditTaskDialogListener?=null

    companion object{
        fun newInstance(title:Int,todo: TodoResponse): EditTaskDialogFragment {
            val editTaskDialogFragment = EditTaskDialogFragment()
            val args = Bundle()
            args.putParcelable("todo",todo)
            args.putInt("dialog_title",title)
            editTaskDialogFragment.arguments = args
            return editTaskDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getInt("dialog_title")
        val todoItem = arguments?.getParcelable<TodoResponse>("todo")
        val builder = AlertDialog.Builder(activity)
        if (title != null) {
            builder.setTitle(title)
        }
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_task,null)
        val task = dialogView.findViewById<EditText>(R.id.task)
        task.setText(todoItem?.title)
        val content = dialogView.findViewById<EditText>(R.id.content)
        content.setText(todoItem?.content)
        val btnArchive = dialogView.findViewById<Button>(R.id.btnArchive)
        btnArchive.setOnClickListener{
            id->editTaskDialogListener?.onEditDialogArchiveClick(this, todoItem!!)
        }
        builder.setView(dialogView)
            .setPositiveButton(R.string.save) { dialog, id-> editTaskDialogListener?.onEditDialogPositiveClick(this, todoItem!!, task.text.toString(),content.text.toString())}
            .setNegativeButton(android.R.string.cancel,{dialog,id -> editTaskDialogListener?.onEditDialogNegativeClick(this)})
        return builder.create()
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        try {
            editTaskDialogListener = activity as EditTaskDialogListener
        }catch(e: ClassCastException){
            throw ClassCastException(activity.toString() + "must implement EditTaskDialogListener")
        }
    }
}