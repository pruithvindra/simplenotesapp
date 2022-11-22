package com.karthik.notesapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NoteEditorActivity : AppCompatActivity() {
    var note: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)
        val editText : EditText  = findViewById(R.id.editText);
        val intent : Intent = getIntent();

        // Accessing the data using key and value
        note = intent.getStringExtra("note");
        Log.d("testtt", "onCreate:$note   ")
        if (note != "-1") {

            Log.d("testtt", "onCreate:$note   ")
            editText.setText(note);
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                // add your code here
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//                MainActivity().notes.set(note!!, charSequence.toString())
//                MainActivity().arrayAdapter!!.notifyDataSetChanged()
//                // Creating Object of SharedPreferences to store data in the phone
//
            }

            override fun afterTextChanged(editable: Editable) {
                // add your code here
            }
        })
    }


    override fun onBackPressed() {
        val editText : EditText  = findViewById(R.id.editText);
        val stringToPassBack: String = editText.getText().toString()

        // Put the String to pass back into an Intent and close this activity

        // Put the String to pass back into an Intent and close this activity
        val intent = Intent()
        intent.putExtra("keyName", stringToPassBack)
        setResult(RESULT_OK,intent);

        finish(); super.onBackPressed()
    }

}