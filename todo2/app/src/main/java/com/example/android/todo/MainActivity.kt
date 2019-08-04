package com.example.android.todo

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todo.api.TodoClient
import com.example.android.todo.api.TodoResponse
import com.example.android.todo.fragment.EditTaskDialogFragment
import com.example.android.todo.fragment.NewTaskDialogFragment
import com.example.android.todo.recycleview.TodoAdapter
import com.example.android.todo.recycleview.TodoRowListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener,
    TodoRowListener,
    EditTaskDialogFragment.EditTaskDialogListener  {


    private var todoList =arrayListOf<TodoResponse>()
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.adapter= TodoAdapter(this, todoList)
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        fab.setOnClickListener { showNewTaskUI() }

        getData()

    }
    private fun getData(){
        val call: Call<List<TodoResponse>> = TodoClient.getClient.getTodoList()
        call.enqueue(object: Callback<List<TodoResponse>>{
            override fun onResponse(call: Call<List<TodoResponse>>?, response: Response<List<TodoResponse>>?) {
                todoList.clear()
                todoList.addAll(response!!.body()!!)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<TodoResponse>>?, t: Throwable?) {}
        })
    }
    private fun getArchiveData(){
        val call: Call<List<TodoResponse>> = TodoClient.getClient.getArchivedTodo()
        call.enqueue(object: Callback<List<TodoResponse>>{
            override fun onResponse(call: Call<List<TodoResponse>>?, response: Response<List<TodoResponse>>?) {
                todoList.clear()
                todoList.addAll(response!!.body()!!)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<List<TodoResponse>>?, t: Throwable?) {}
        })
    }

    override fun modifyItemState(itemObjectId: String?, isDone: Boolean) {
        val call  = TodoClient.getClient.doneTodo(itemObjectId!!,!isDone)
        call.enqueue(object : Callback<TodoResponse>{
            override fun onResponse(call:Call<TodoResponse>, response: Response<TodoResponse>){
            }
            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {}
        })
    }

    override fun onItemDelete(itemObjectId: String?) {
        val call  = TodoClient.getClient.deleteTodo(itemObjectId)
        call.enqueue(object: Callback<TodoResponse>{
            override fun onResponse(call:Call<TodoResponse>, response: Response<TodoResponse>){
                Log.i("","post deleted in API." + response.body()!!)
                getData()
                Snackbar.make(fab, "Task Deleted Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()


            }

            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {

            }
        })
    }
    override fun onItemSelected(itemObject: TodoResponse){
        val editFragment = EditTaskDialogFragment.newInstance(R.string.edit_task_dialog_title,itemObject)
        editFragment.show(supportFragmentManager , "edittask")
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    fun showNewTaskUI(){
        val newFragment = NewTaskDialogFragment.newInstance(R.string.add_new_task_dialog_title)
        newFragment.show(supportFragmentManager , "newtask")
    }
    override fun onNewDialogPositiveClick(dialog: DialogFragment, task: String,content:String) {
        val call = TodoClient.getClient.addTodo(task,content)
        call.enqueue(object : Callback<TodoResponse>{
            override fun onResponse(call:Call<TodoResponse>, response: Response<TodoResponse>){
                Log.i("","post submitted to API." + response.body()!!)
                Snackbar.make(fab, "Task Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                getData()
            }
            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {}
        })
    }

    override fun onNewDialogNegativeClick(dialog: DialogFragment) {}
    override fun onEditDialogPositiveClick(dialog: DialogFragment, todo: TodoResponse, newTitle:String, newContent:String) {
        val call = TodoClient.getClient.editTodo(todo.id!!, newTitle, newContent,todo.done,todo.archived)
        call.enqueue(object : Callback<TodoResponse>{
            override fun onResponse(call:Call<TodoResponse>, response: Response<TodoResponse>){
                Log.i("","post submitted to API." + response.body()!!)
                Snackbar.make(fab, "Todo Updated Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                getData()
            }
            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {}
        })
    }

    override fun onEditDialogNegativeClick(dialog: DialogFragment){}

    override fun onEditDialogArchiveClick(dialog: DialogFragment, todo: TodoResponse) {
        val call = TodoClient.getClient.archiveTodo(todo.id!!,!todo.archived)
        call.enqueue(object : Callback<TodoResponse>{
            override fun onResponse(call:Call<TodoResponse>, response: Response<TodoResponse>){
                Snackbar.make(fab, "Todo Archive/Unarchive Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
                getData()
                dialog.dismiss()
            }
            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {}
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_archive -> {
                getArchiveData()
                true
            }

            R.id.action_todo-> {
                getData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
