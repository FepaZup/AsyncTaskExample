package br.com.zup.asynctaskexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.zup.asynctaskexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var myVariable = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.numberPicker.minValue = 5
        binding.numberPicker.maxValue = 20

        binding.btnDormirAsync.setOnClickListener {
            val task = AsyncTaskExample(this)
            task.execute(binding.numberPicker.value)
        }

        binding.btnDormirSemAsyncTask.setOnClickListener {

            binding.progressBar.visibility = View.VISIBLE
            binding.tvRetorno.text = ""
            val tempoTotal = binding.numberPicker.value.toLong()*1000

            Toast.makeText(this, "Começou a dormir sem AsyncTask", Toast.LENGTH_SHORT).show()
            Thread.sleep(tempoTotal )
            Toast.makeText(this, "Acabou de dormir sem AsyncTask", Toast.LENGTH_SHORT).show()
            binding.tvRetorno.text =
                "Android dormiu por ${binding.numberPicker.value} segundos sem AsyncTask"
            binding.progressBar.visibility = View.GONE
        }
    }
}
