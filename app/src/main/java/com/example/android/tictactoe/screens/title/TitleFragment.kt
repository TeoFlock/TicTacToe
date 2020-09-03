package com.example.android.tictactoe.screens.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.tictactoe.R
import com.example.android.tictactoe.databinding.FragmentTitleBinding
import kotlin.time.measureTimedValue


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentTitleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: TitleViewModel

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
        _binding = FragmentTitleBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)

        _binding?.buttonPlay?.setOnClickListener {
            viewModel.onTitleFinish()
        }

        viewModel.editNamesFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished) {
                viewModel.enterPlayer1Name(_binding?.editTextPlayer1?.text.toString())
                viewModel.enterPlayer2Name(_binding?.editTextPlayer2?.text.toString())

                startGame()
                viewModel.onTitleFinishDone()
            }
        })

        _binding?.buttonAbout?.setOnClickListener {
            showAbout()
        }

        return binding.root
    }

    private fun startGame() {
        val argPlayer1 = viewModel.player1Name.value ?: "NameError"
        val argPlayer2 = viewModel.player2Name.value ?: "NameError"

        val action = TitleFragmentDirections.actionTitleFragmentToPlayGroundFragment(argPlayer1, argPlayer2)
        this.findNavController().navigate(action)
    }

    private fun showAbout() {
        this.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToAboutFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TitleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TitleFragment()
                .apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}