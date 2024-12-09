package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kenkoro.practice.githubClone.reposViewer.presentation.models.RepoUi
import com.kenkoro.projects.githubClone.databinding.RepoItemBinding

class ReposAdapter(
  private val repos: List<RepoUi>,
) : RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {
  inner class RepoViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int,
  ): RepoViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = RepoItemBinding.inflate(inflater, parent, false)

    return RepoViewHolder(binding)
  }

  override fun onBindViewHolder(
    holder: RepoViewHolder,
    position: Int,
  ) {
    val repo = repos[position]
    holder.binding.apply {
      val rootContext = root.context
      tvRepoName.text = repo.name
      tvRepoMostUsedLanguage.text = repo.language
      tvRepoMostUsedLanguage.setTextColor(ContextCompat.getColor(rootContext, repo.colorRes))

      if (repo.description.isNullOrBlank()) {
        tvRepoDescription.visibility = View.GONE
      } else {
        tvRepoDescription.text = repo.description
      }
      // TODO: Set here onClick method
    }
  }

  override fun getItemCount(): Int = repos.size
}