package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kenkoro.practice.githubClone.core.domain.util.NetworkError
import com.kenkoro.practice.githubClone.core.domain.util.onError
import com.kenkoro.practice.githubClone.core.domain.util.onSuccess
import com.kenkoro.practice.githubClone.reposViewer.domain.AppRepository
import com.kenkoro.practice.githubClone.reposViewer.domain.UserInfo
import com.kenkoro.practice.githubClone.reposViewer.presentation.reposList.util.NetworkErrorMessageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
  @Inject
  constructor(
    private val appRepository: AppRepository,
    private val errorMessageProvider: NetworkErrorMessageProvider,
  ) : ViewModel() {
    private val _token by lazy {
      MutableLiveData<String>()
    }
    val token: LiveData<String> = _token
    private val _state by lazy {
      MutableLiveData<State>(State.Idle)
    }
    val state: LiveData<State> = _state

  /*
   * Decided to use a shared flow, because a state flow keeps emitting the last value
   * after a configuration change (maybe because a new collector comes into play after
   * the fragment recreation).
   */
    private val _actions by lazy {
      MutableSharedFlow<Action>()
    }
    val actions = _actions.asSharedFlow()

    fun onSignInButtonPressed() {
      _state.value = State.Loading
      val bearerToken = toBearerToken(token.value).trim()
      viewModelScope.launch {
        val result =
          withContext(Dispatchers.IO) {
            appRepository.signIn(bearerToken)
          }
        result
          .onSuccess(this@AuthViewModel::onSuccess)
          .onError(this@AuthViewModel::onError)
      }
    }

    private fun onSuccess(userInfo: UserInfo) {
      _state.value = State.Idle
      viewModelScope.launch {
        _actions.emit(Action.RouteToMain)
      }
    }

    private fun onError(networkError: NetworkError) {
      val message = errorMessageProvider.getMessage(networkError)
      val reason = produceInvalidInputReason(message)
      viewModelScope.launch {
        _actions.emit(Action.ShowError(message))
      }
      _state.value = State.InvalidInput(reason)
    }

    private fun produceInvalidInputReason(message: String): String {
      return if (token.value.isNullOrBlank()) {
        "Empty PAT"
      } else {
        message
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