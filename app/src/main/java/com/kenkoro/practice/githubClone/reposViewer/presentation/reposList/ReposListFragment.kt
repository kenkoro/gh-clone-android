package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kenkoro.practice.githubClone.reposViewer.presentation.models.RepoUi
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.ReposListFragmentBinding

class ReposListFragment : Fragment() {
  private var _binding: ReposListFragmentBinding? = null
  private val binding: ReposListFragmentBinding
    get() = _binding!!

  private lateinit var rvRepos: RecyclerView

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
    setupBindings()

    val repos =
      listOf(
        RepoUi(
          id = 0,
          name = "moko-web3",
          description =
            "Ethereum Web3 implementation for mobile (android & ios) Kotlin " +
              "Multiplatform development",
          language = "Kotlin",
          colorRes = R.color.kotlin,
        ),
        RepoUi(
          id = 1,
          name = "moko-resources",
          description =
            "Resources access for mobile (android & ios) Kotlin Multiplatform" +
              "development",
          language = "Kotlin",
          colorRes = R.color.kotlin,
        ),
      )
    val rvAdapter =
      ReposAdapter(repos).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
      }
    rvRepos.adapter = rvAdapter
    rvRepos.layoutManager = LinearLayoutManager(requireContext())
  }

  private fun setupBindings() {
    rvRepos = binding.rvRepos
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}