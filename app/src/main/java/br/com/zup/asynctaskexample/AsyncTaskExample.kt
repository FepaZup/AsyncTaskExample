package br.com.zup.asynctaskexample

import android.os.AsyncTask
import android.view.View
import android.widget.Toast
import java.lang.ref.WeakReference

class AsyncTaskExample (context: MainActivity) : AsyncTask<Int, String, String?>() {

        private var resp: String? = null

    // Não se preocupem com esse WeakReference, é só uma forma segura para acessar a MainActivity
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            //Esse código roda na UI Thread

            //aqui guardo uma referência a MainActivity
            val activity = activityReference.get()
            //levando em consideração o ciclo de vida da activity
            if (activity == null || activity.isFinishing) return

            activity.binding.progressBar.visibility = View.VISIBLE
            activity.binding.tvRetorno.text = ""
        }

        override fun doInBackground(vararg params: Int?): String? {
            //Esse código roda na Thread de trabalho
            publishProgress("Começou a dormir com AsyncTask") // Invoca onProgressUpdate()
            try {
                val time = params[0]?.times(1000)
                time?.toLong()?.let { Thread.sleep(it / 2) }
                publishProgress("Metade do tempo já passou") // Invoca onProgressUpdate()
                time?.toLong()?.let { Thread.sleep(it / 2) }
                publishProgress("Acabou a soneca") // Invoca onProgressUpdate()
                resp = "Android dormiu por " + params[0] + " segundos"
            } catch (e: InterruptedException) {
                e.printStackTrace()
                resp = e.message
            } catch (e: Exception) {
                e.printStackTrace()
                resp = e.message
            }

            return resp
        }


        override fun onPostExecute(result: String?) {
            //Esse código roda na UI Thread

            //aqui guardo uma referência a MainActivity
            val activity = activityReference.get()
            //levando em consideração o ciclo de vida da activity
            if (activity == null || activity.isFinishing) return


            activity.binding.progressBar.visibility = View.GONE
            activity.binding.tvRetorno.text = result.let { it }
            activity.myVariable = 100
        }

        override fun onProgressUpdate(vararg text: String?) {
            //Esse código roda na UI Thread

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            Toast.makeText(activity, text.firstOrNull(), Toast.LENGTH_SHORT).show()

        }


}