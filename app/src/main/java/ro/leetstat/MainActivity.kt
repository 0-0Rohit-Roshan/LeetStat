package ro.leetstat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.coroutines.NonCancellable.start
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    //Initiate requeswtQueue of volley
    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appnetwork = BasicNetwork(HurlStack())
        val appcache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        requestQueue = RequestQueue(appcache, appnetwork).apply {
            start()
        }

        val search = findViewById<Button>(R.id.search)
        val userinput = findViewById<EditText>(R.id.userinput)
        // Now you can refer to the view using the variable
        search.setOnClickListener {
            var input = userinput.text.toString()
            fetchData(input)
        }
    }

    fun fetchData( input: String){
        val totalSolved = findViewById<TextView>(R.id.totalSolved)
        val totalQuestions = findViewById<TextView>(R.id.totalQuestions)
        //val plot = findViewById<TextView>(R.id.plot)
        val easySolved : TextView = findViewById(R.id.easySolved) as TextView
        val totalEasy : TextView = findViewById<TextView>(R.id.totalEasy)
        val mediumSolved : TextView = findViewById<TextView>(R.id.mediumSolved)
        val totalMedium : TextView = findViewById<TextView>(R.id.totalMedium)
        val hardSolved : TextView = findViewById<TextView>(R.id.hardSolved)
        val totalHard : TextView = findViewById<TextView>(R.id.totalHard)
        val acceptanceRate : TextView = findViewById<TextView>(R.id.acceptanceRate)
        val ranking : TextView = findViewById<TextView>(R.id.ranking)
        val contributionPoints : TextView = findViewById<TextView>(R.id.contributionPoints)
        val reputation : TextView = findViewById<TextView>(R.id.reputation)
        val submissionCalendar : TextView = findViewById<TextView>(R.id.submissionCalendar)
        val logu : TextView = findViewById<TextView>(R.id.logu)
        val url = "https://leetcode-stats-api.herokuapp.com/$input"
        logu.text = url
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                //Use try catch not if else
                try {
                    totalSolved.text = "TotalSolved : " + response.getString("totalSolved")
                    totalQuestions.text = "TotalQuestions : "+response.getString("totalQuestions")
                    easySolved.text = "easySolved : "+response.getString("easySolved")
                    totalEasy.text = "totalEasy : "+response.getString("totalEasy")
                    mediumSolved.text = "mediumSolved : "+response.getString("mediumSolved")
                    totalMedium.text = "totalMedium : "+response.getString("totalMedium")
                    hardSolved.text = "hardSolved : "+response.getString("hardSolved")
                    totalHard.text = "totalHard : "+response.getString("totalHard")
                    acceptanceRate.text = "acceptanceRate : "+response.getString("acceptanceRate")
                    ranking.text = "ranking : "+response.getString("ranking")
                    contributionPoints.text = "contributionPoints : "+response.getString("contributionPoints")
                    reputation.text = "reputation : "+response.getString("reputation")
                    submissionCalendar.text = "submissionCalendar : "+response.getString("submissionCalendar")
                }catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Log.d("vol",error.toString())
                logu.text = error.toString()
            }
        )

        requestQueue.add(jsonObjectRequest)
    }
}