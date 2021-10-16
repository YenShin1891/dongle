package com.example.ecomap4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecomap4.databinding.FragmentMapBinding

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.addPinButton.setOnClickListener {
//            //Turn on camera
//            val toast = Toast.makeText(this.context, "fragment button works!", Toast.LENGTH_SHORT)
//            toast.show()
//            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}