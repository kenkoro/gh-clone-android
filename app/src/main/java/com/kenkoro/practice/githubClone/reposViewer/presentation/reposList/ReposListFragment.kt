package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kenkoro.projects.githubClone.databinding.ReposListFragmentBinding

class ReposListFragment : Fragment() {
  private var _binding: ReposListFragmentBinding? = null
  private val binding: ReposListFragmentBinding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = ReposListFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?,
  ) {
    super.onViewCreated(view, savedInstanceState)
    // TODO
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}