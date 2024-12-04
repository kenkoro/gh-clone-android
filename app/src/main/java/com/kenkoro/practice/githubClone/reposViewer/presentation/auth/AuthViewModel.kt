package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.onError
import com.kenkoro.practice.githubClone.core.domain.util.onSuccess
import com.kenkoro.practice.githubClone.reposViewer.domain.ReposViewerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
  @Inject
  constructor(
    private val appRepository: ReposViewerRepository,
  ) : ViewModel() {
    private val _token by lazy {
      MutableLiveData<String>()
    }
    val token: LiveData<String> = _token
    private val _state by lazy {
      MutableLiveData<State>(State.Idle)
    }
    val state: LiveData<State> = _state

    fun onSignInButtonPressed(): Flow<Action> =
      flow {
        _state.value = State.Loading
        val bearerToken = toBearerToken(token.value).trim()
        appRepository.signIn(bearerToken)
          .onSuccess { userInfo ->
            // TODO: Save userInfo
            Log.d("kenkoro", userInfo.login)
            _state.value = State.Idle
            emit(Action.RouteToMain)
          }
          .onError { networkError ->
            val message =
              when (networkError) {
                NetworkError.NoInternet -> "No Internet connection"
                NetworkError.Serialization -> "Serialization failed"
                NetworkError.ServerError -> "Server error"
                NetworkError.Unknown -> "Unknown error"
                NetworkError.NotModified -> "The data in request was not modified"
                NetworkError.RequiresAuthentication -> "Requires authentication"
                NetworkError.Forbidden -> "Forbidden resource"
              }
            _state.value = State.InvalidInput(message)
            emit(Action.ShowError(message))
          }
      }

    private fun toBearerToken(token: String?): String {
      val prefix = "Bearer"
      return "$prefix ${token ?: ""}"
    }

    fun onTokenChanged(token: String) {
      if (token != _token.value) {
        _token.value = token
      }
    }

    sealed interface State {
      data object Idle : State

      data object Loading : State

      data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
      data class ShowError(val message: String) : Action

      data object RouteToMain : Action
    }
  }