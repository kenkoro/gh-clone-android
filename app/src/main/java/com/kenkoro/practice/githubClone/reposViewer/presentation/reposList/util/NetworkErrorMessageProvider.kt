package com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util

import android.content.Context
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.projects.githubClone.R

class NetworkErrorMessageProvider(
  private val appContext: Context,
) {
  fun getMessage(networkError: NetworkError): String {
    return with(appContext) {
      when (networkError) {
        NetworkError.NoInternet -> getString(R.string.no_internet_error_msg)
        NetworkError.Serialization -> getString(R.string.serialization_error_msg)
        NetworkError.ServerError -> getString(R.string.server_error_msg)
        NetworkError.Unknown -> getString(R.string.unknown_error_msg)
        NetworkError.NotModified -> getString(R.string.not_modified_error_msg)
        NetworkError.RequiresAuthentication -> getString(R.string.auth_error_msg)
        NetworkError.Forbidden -> getString(R.string.forbidden_error_msg)
      }
    }
  }
}