package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kenkoro.projects.githubClone.databinding.AuthFragmentBinding

class AuthFragment : Fragment() {
  private var _binding: AuthFragmentBinding? = null
  private val binding: AuthFragmentBinding
    get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = AuthFragmentBinding.inflate(inflater, container, false)
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