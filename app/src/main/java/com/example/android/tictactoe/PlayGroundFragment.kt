package com.example.android.tictactoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.android.tictactoe.Data.Game
import com.example.android.tictactoe.Data.Player
import com.example.android.tictactoe.databinding.FragmentPlayGroundBinding
import androidx.navigation.NavArgs as NavArgs1

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

    private lateinit var player1: Player
    private lateinit var player2: Player



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

        val player1 = Player(args.player1Name)
        val player2 = Player(args.player2Name)

        val game = Game(player1, player2)

        binding.nextPlayer.text = "${game.currentPlayer.name} ist dran!"


        // Inflate the layout for this fragment
        return binding.root
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
            PlayGroundFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
