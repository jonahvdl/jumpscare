package com.jumpscarealertapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [movie.newInstance] factory method to
 * create an instance of this fragment.
 */
class movie : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var is_playing = false
    var times = listOf(365, 1174, 1221, 1375, 1693, 1725, 2108, 2146, 3214, 3454, 3559, 4431, 4750, 4896, 4963, 5077, 5890, 6343, 6529, 6585, 6748, 6992)

    override fun onCreate(savedInstanceState: Bundle?) {
        println(savedInstanceState.toString())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        view.findViewById<TextView>(R.id.movie_text).text = arguments?.getString("film").toString()

        view.findViewById<ProgressBar>(R.id.progressBar).progress = 0
        view.findViewById<TextView>(R.id.countdown).text = "first jumpscare in 365 seconds"
        view.findViewById<Button>(R.id.start_btn)
            .setOnClickListener {
                if (is_playing) {
                    view.findViewById<Button>(R.id.start_btn).text =
                        "Start"
                    view.findViewById<Button>(R.id.start_btn).setBackgroundColor(Color.parseColor("#8BC34A"))
                    is_playing = !is_playing
                } else {
                    view.findViewById<Button>(R.id.start_btn).text =
                        "Stop"
                    view.findViewById<Button>(R.id.start_btn).setBackgroundColor(Color.RED)
                    is_playing = !is_playing

                }
            }
        view.findViewById<Button>(R.id.reset_btn)
            .setOnClickListener {
                view.findViewById<ProgressBar>(R.id.progressBar).progress = 0
                view.findViewById<TextView>(R.id.countdown).text = "next jumpscare in 365 seconds"
            }
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            @SuppressLint("CutPasteId")
            override fun run() {
                mainHandler.postDelayed(this, 1000)
                if (is_playing) {
                    view.findViewById<ProgressBar>(R.id.progressBar).progress =
                        view.findViewById<ProgressBar>(R.id.progressBar).progress + 1
                    var nearestTime = Int.MAX_VALUE
                    var nearestTimeDeltaT = Int.MAX_VALUE
                    for (time in times) {
                        println(view.findViewById<ProgressBar>(R.id.progressBar).progress.toString())
                        val delta_t = time - view.findViewById<ProgressBar>(R.id.progressBar).progress
                        println("$time $delta_t")
                        if (delta_t <= 0) {
                            continue
                        }
                        if (delta_t < nearestTimeDeltaT){
                            nearestTime = time
                            nearestTimeDeltaT = delta_t
                       }
                    }
                    view.findViewById<TextView>(R.id.countdown).text = "next jumpscare in $nearestTimeDeltaT seconds"
                    if (nearestTimeDeltaT == Int.MAX_VALUE) {
                        view.findViewById<TextView>(R.id.countdown).text = "         no more jumpscares"
                    }
                }
            }
        })

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment movie.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            movie().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}