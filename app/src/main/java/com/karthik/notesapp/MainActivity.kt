package com.karthik.notesapp



import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu

import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    var notes: ArrayList<String> = ArrayList()
    var arrayAdapter: ArrayAdapter<*>? = null
    var index :Int? = null
    var toolbar :Toolbar? = null

    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar =  findViewById(R.id.toolbar)
        toolbar!!.inflateMenu(R.menu.add_note_menu);
        toolbar!!.setOnMenuItemClickListener(this)




        val listView : ListView= findViewById(R.id.listView)
        val sharedPreferences =
            applicationContext.getSharedPreferences("com.karthik.notesapp", Context.MODE_PRIVATE)
        val set = sharedPreferences.getStringSet("notes", null) as HashSet<String>?

        if (set == null) {
            notes.add("Example note")
        } else {
            notes = ArrayList(set)
        }

        arrayAdapter =
            ArrayAdapter<Any?>(this, R.layout.list_item,R.id.text_view,
                notes as List<Any?>
            )

        Log.d("222", "$arrayAdapter $notes ")
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener { adapterView, view, i, l  ->
            index = i;
            val intent = Intent(applicationContext, NoteEditorActivity::class.java)
            intent.putExtra("note", notes.get(i))
            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);



        }





        listView.onItemLongClickListener =
            OnItemLongClickListener { adapterView, view, i, l ->
                val itemToDelete = i
                // To delete the data from the App
                // To delete the data from the App
                AlertDialog.Builder(this@MainActivity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Are you sure?")
                    .setMessage("Do you want to delete this note?")
                    .setPositiveButton(
                        "Yes",
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            notes.removeAt(itemToDelete)
                            arrayAdapter!!.notifyDataSetChanged()
                            val sharedPreferences = applicationContext.getSharedPreferences(
                                "com.example.notes",
                                MODE_PRIVATE
                            )
                            val set: HashSet<String> = HashSet(MainActivity().notes)
                            sharedPreferences.edit().putStringSet("notes", set).apply()
                        }).setNegativeButton("No", null).show()

                 true
            }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TAG", "heloooo yooo $requestCode -- $resultCode")
        if (requestCode === SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode === RESULT_OK) {



                // Get String data from Intent
                val returnString: String? = data?.getStringExtra("keyName") ?: " hh "
                notes.set(index!!,returnString!! )
                arrayAdapter!!.notifyDataSetChanged()
                // Set text view with string
                val sharedPreferences = applicationContext.getSharedPreferences("com.karthik.notesapp", Context.MODE_PRIVATE)
                val set: HashSet<String> = HashSet(notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()

            }
        }
    }






    override fun onMenuItemClick(item: MenuItem?): Boolean {


        Log.d("TAG", "heloooo  ${item!!.itemId == R.id.add_note} ")

        notes.add("add note here");
        val sharedPreferences = applicationContext.getSharedPreferences("com.karthik.notesapp", Context.MODE_PRIVATE)
        val set: HashSet<String> = HashSet(notes)
        sharedPreferences.edit().putStringSet("notes", set).apply()
        arrayAdapter!!.notifyDataSetChanged()

        return false
    }


}