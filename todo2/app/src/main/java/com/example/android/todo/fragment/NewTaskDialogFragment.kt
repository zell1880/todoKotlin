package com.example.android.todo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.android.todo.R

class NewTaskDialogFragment:DialogFragment(){
    interface NewTaskDialogListener{
        fun onNewDialogPositiveClick(dialog: DialogFragment,task: String,content:String)
        fun onNewDialogNegativeClick(dialog: DialogFragment)
    }

    var newTaskDialogListener: NewTaskDialogListener?=null

    companion object{
        fun newInstance(title:Int): NewTaskDialogFragment {
            val newTaskDialogFragment = NewTaskDialogFragment()
            val args = Bundle()
            args.putInt("dialog_title",title)
            newTaskDialogFragment.arguments = args
            return newTaskDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getInt("dialog_title")
        val builder = AlertDialog.Builder(activity)
        if (title != null) {
            builder.setTitle(title)

        }
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_new_task,null)
        val task = dialogView.findViewById<EditText>(R.id.task)
        val content = dialogView.findViewById<EditText>(R.id.content)
        builder.setView(dialogView)
            .setPositiveButton(R.string.save) { dialog, id-> newTaskDialogListener?.onNewDialogPositiveClick(this,task.text.toString(),content.text.toString())}
            .setNegativeButton(android.R.string.cancel) { dialog, id -> newTaskDialogListener?.onNewDialogNegativeClick(this)}
        return builder.create()
    }

    override fun onAttach(context : Context) {
        super.onAttach(context)
        try {
            newTaskDialogListener = activity as NewTaskDialogListener
        }catch(e:ClassCastException){
            throw ClassCastException(activity.toString() + "must implement NewTaskDialogListener")
        }
    }
}