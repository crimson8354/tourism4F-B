package com.example.practice

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.practice.databinding.FragmentDetailBinding
import kotlinx.parcelize.Parcelize

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ADDRESS = "address"
private const val ARG_COORDINATE = "coordinate"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var address: String? = null
    private var coordinate: Coordinate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            address = it.getString(ARG_ADDRESS)
            coordinate = it.getParcelable(ARG_COORDINATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailTextView.text = address
        binding.mapWebView.settings.javaScriptEnabled = true
        Log.i("test", "${coordinate!!.latitude},${coordinate!!.longitude}")
        binding.mapWebView.loadUrl("https://www.google.com/maps/@${coordinate!!.latitude},${coordinate!!.longitude},15z")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(address: String, coordinate: Coordinate) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ADDRESS, address)
                    putParcelable(ARG_COORDINATE, coordinate)
                }
            }
    }
}

@Parcelize
data class Coordinate(val longitude: Double, val latitude: Double): Parcelable