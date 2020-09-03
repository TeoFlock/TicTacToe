package com.example.android.tictactoe.screens.playground

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.android.tictactoe.Data.Field
import com.example.android.tictactoe.Data.Player
import com.example.android.tictactoe.databinding.FragmentPlayGroundBinding
import java.lang.IllegalArgumentException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayGroundFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlayGroundFragment : Fragment()  {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPlayGroundBinding? = null
    private val binding get() = _binding!!

    val args: PlayGroundFragmentArgs by navArgs()

    private lateinit var viewModel: PlaygroundViewModel
    private lateinit var viewModelFactory: PlayGroundViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayGroundBinding.inflate(inflater, container, false)

        viewModelFactory = PlayGroundViewModelFactory(args.player1Name, args.player2Name)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaygroundViewModel::class.java)

        viewModel.currentPlayer.observe(viewLifecycleOwner, Observer { player ->
            _binding?.nextPlayer?.text = "${player} ist dran!"
        })

        viewModel.currentPlayerScored.observe(viewLifecycleOwner, Observer { hasScored ->
            if(hasScored) {
                showWinner()
                viewModel.onScoredDone()
            }
        })

        viewModel.chosenFieldKey.observe(viewLifecycleOwner, Observer { key ->
            val button = accessButtonByString(key) ?: throw IllegalAccessError("binding object not found")
            button.text = viewModel.currentPlayerSign.value
        })

        _binding?.buttonA1?.setOnClickListener {
            viewModel.onButtonClick("a1")
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showWinner() {
        val showText = "${viewModel.currentPlayer.value} has scored!"
        Toast.makeText(this.context, showText, Toast.LENGTH_LONG).show()
    }

    private fun accessButtonByString(buttonKey: String): Button? {
        return when(buttonKey) {
            "a1" -> _binding?.buttonA1
            "a2" -> _binding?.buttonA2
            "a3" -> _binding?.buttonA3
            "b1" -> _binding?.buttonB1
            "b2" -> _binding?.buttonB2
            "b3" -> _binding?.buttonB3
            "c1" -> _binding?.buttonC1
            "c2" -> _binding?.buttonC2
            "c3" -> _binding?.buttonC3
            else -> throw IllegalArgumentException("No Button found for key $buttonKey")
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayGroundFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlayGroundFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
