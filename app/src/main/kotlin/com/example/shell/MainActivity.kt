package com.example.shell

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import android.util.Log
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.method.ScrollingMovementMethod
import com.example.shell.databinding.ActivityMainBinding
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var commandRun: Button
    private lateinit var Output: TextView
    private lateinit var command_container: EditText
    init {
        lifecycleScope.launchWhenCreated {
            copyAssets(context)
            arrayOf(
            "date",
            "echo ${context.filesDir.absolutePath}"
            ).forEach { command ->
                val result = async { shell(command) }
                Toast.makeText(context, result.await(), Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        context = this@MainActivity
        
        commandRun = findViewById(R.id.button)
        Output = findViewById(R.id.textView2)
        command_container = findViewById(R.id.Command_container)

        Output.setMovementMethod(ScrollingMovementMethod())
    }
    public fun commandRunner(v: View){
        var command = command_container.getText().toString()
        lifecycleScope.launchWhenCreated {
            val result = async { shell(command) }
            Output.setText(result.await())
        }
    }
}
