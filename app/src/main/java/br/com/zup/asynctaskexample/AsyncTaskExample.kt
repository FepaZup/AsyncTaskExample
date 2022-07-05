package br.com.zup.asynctaskexample

import android.os.AsyncTask
import android.view.View
import android.widget.Toast
import java.lang.ref.WeakReference

class AsyncTaskExample (context: MainActivity) : AsyncTask<Int, String, String?>() {

        private var resp: String? = null
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun onPreExecute() {
            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
            activity.binding.progressBar.visibility = View.VISIBLE
            activity.binding.tvRetorno.text = ""
        }

        override fun doInBackground(vararg params: Int?): String? {
            publishProgress("Começou a dormir com AsyncTask") // Calls onProgressUpdate()
            try {
                val time = params[0]?.times(1000)
                time?.toLong()?.let { Thread.sleep(it / 2) }
                publishProgress("Metade do tempo já passou") // Calls onProgressUpdate()
                time?.toLong()?.let { Thread.sleep(it / 2) }
                publishProgress("Acabou a soneca") // Calls onProgressUpdate()
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

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return
            activity.binding.progressBar.visibility = View.GONE
            activity.binding.tvRetorno.text = result.let { it }
            activity.myVariable = 100
        }

        override fun onProgressUpdate(vararg text: String?) {

            val activity = activityReference.get()
            if (activity == null || activity.isFinishing) return

            Toast.makeText(activity, text.firstOrNull(), Toast.LENGTH_SHORT).show()

        }


}