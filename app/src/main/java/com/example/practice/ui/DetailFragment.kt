package com.example.practice.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.practice.databinding.FragmentDetailBinding

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

        val info = args.restaurant
        binding.toolbar.title = info.name
        binding.introTextView.text = info.desc
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        if (info.picture1.isNotBlank()) {
            Glide.with(binding.root).load(info.picture1).into(binding.mainBarImageView)
            Glide.with(binding.root).load(info.picture1).into(binding.picture1)
        }
        if (info.picture2.isNotBlank()) {
            Glide.with(binding.root).load(info.picture2).into(binding.picture2)
        }
        if (info.picture3.isNotBlank()) {
            Glide.with(binding.root).load(info.picture3).into(binding.picture3)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}