package com.kenkoro.practice.githubClone.reposViewer.presentation.repoDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.kenkoro.practice.githubClone.core.navigation.util.NavArguments
import com.kenkoro.projects.githubClone.databinding.FragmentRepoDetailsBinding

class RepoDetailsFragment : Fragment() {
  private var _binding: FragmentRepoDetailsBinding? = null
  private val binding: FragmentRepoDetailsBinding
    get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setFragmentResultListener(NavArguments.ROUTE_TO_REPO_DETAILS_REQUEST) { _, bundle ->
      val repoId = bundle.getInt(NavArguments.REPO_ID)
      Log.d("kenkoro", "Repo id: $repoId")
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View? {
    _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
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