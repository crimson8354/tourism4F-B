package com.example.practice.ui

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.practice.databinding.FragmentDetailBinding
import kotlinx.parcelize.Parcelize

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailTextView.text = args.address
        binding.mapWebView.settings.javaScriptEnabled = true
        binding.mapWebView.loadUrl("https://www.google.com/maps/@${args.coordinate.latitude},${args.coordinate.longitude},15z")
        Log.i("test", "${args.coordinate.latitude},${args.coordinate.longitude}")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

@Parcelize
data class Coordinate(val longitude: Double, val latitude: Double): Parcelable