package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.kenkoro.practice.githubClone.core.navigation.Screen
import com.kenkoro.projects.githubClone.R
import com.kenkoro.projects.githubClone.databinding.AuthFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
  private var _binding: AuthFragmentBinding? = null
  private val binding: AuthFragmentBinding
    get() = _binding!!

  private lateinit var tietAuthToken: TextInputEditText
  private lateinit var tilAuthToken: TextInputLayout
  private lateinit var btnAuth: RelativeLayout
  private lateinit var pbBtnAuth: ProgressBar
  private lateinit var tvBtnAuth: TextView
  private val authViewModel by viewModels<AuthViewModel>()
  private lateinit var alertDialog: AlertDialog

  override fun onAttach(context: Context) {
    super.onAttach(context)
    alertDialog =
      AlertDialog.Builder(requireContext())
        .setTitle(R.string.alert_dialog_title)
        .setMessage(R.string.alert_dialog_message)
        .setPositiveButton(R.string.alert_dialog_positive_btn_text) { dialog, _ ->
          dialog.dismiss()
        }
        .create()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = AuthFragmentBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?,
  ) {
    super.onViewCreated(view, savedInstanceState)
    setupBindings()
    subscribeToObservables()

    tilAuthToken.errorIconDrawable = null
    tietAuthToken.doOnTextChanged { text, _, _, _ ->
      authViewModel.onTokenChanged(text.toString())
      tilAuthToken.error = null
    }
    btnAuth.setOnClickListener { authViewModel.onSignInButtonPressed() }
  }

  private fun setupBindings() {
    tietAuthToken = binding.tietAuthToken
    tilAuthToken = binding.tilAuthToken
    btnAuth = binding.btnAuth
    pbBtnAuth = binding.pbBtnAuth
    tvBtnAuth = binding.tvBtnAuth
  }

  private fun subscribeToObservables() {
    authViewModel.token.observe(viewLifecycleOwner) { token ->
      tietAuthToken.setText(token)
      tietAuthToken.setSelection(token.length)
    }
    authViewModel.state.observe(viewLifecycleOwner) { state ->
      when (state) {
        AuthViewModel.State.Idle -> onIdleState()

        is AuthViewModel.State.InvalidInput -> onInvalidInputState(state.reason)

        AuthViewModel.State.Loading -> onLoadingState()
      }
    }
    viewLifecycleOwner.lifecycleScope.launch {
      /*
       * WARN: Never collect a flow from the UI directly from launch or
       * the launchIn extension function if the UI needs to be updated.
       * These functions process events even when the view is not visible.
       * This behavior can lead to app crashes. To avoid that, use the
       * repeatOnLifecycle API.
       */
      repeatOnLifecycle(Lifecycle.State.STARTED) {
        authViewModel.actions.collectLatest { action ->
          when (action) {
            AuthViewModel.Action.RouteToMain -> onRouteToMainAction()
            is AuthViewModel.Action.ShowError -> onShowErrorAction(action.message)
          }
        }
      }
    }
  }

  private fun onRouteToMainAction() {
    findNavController().navigate(Screen.ReposList.route)
  }

  private fun onShowErrorAction(message: String) {
    if (tilAuthToken.hasFocus()) {
      tilAuthToken.clearFocus()
    }
    alertDialog.setMessage(message)
    alertDialog.show()
  }

  private fun onIdleState() {
    stopAuthAnimationIfItWasLaunched()
  }

  private fun onInvalidInputState(reason: String) {
    if (!tilAuthToken.hasFocus()) {
      tilAuthToken.requestFocus()
    }
    tilAuthToken.error = requireContext().getString(R.string.auth_et_helper)
    stopAuthAnimationIfItWasLaunched()
  }

  private fun onLoadingState() {
    tilAuthToken.error = null
    startAuthAnimationIfItWasNotLaunched()
  }

  private fun stopAuthAnimationIfItWasLaunched() {
    if (pbBtnAuth.isVisible) {
      stopProgressBarAnimation()
    }
  }

  private fun startAuthAnimationIfItWasNotLaunched() {
    if (!pbBtnAuth.isVisible) {
      startProgressBarAnimation()
    }
  }

  private fun startProgressBarAnimation() {
    if (tilAuthToken.hasFocus()) {
      tilAuthToken.clearFocus()
    }
    tvBtnAuth.visibility = View.GONE
    pbBtnAuth.visibility = View.VISIBLE
  }

  private fun stopProgressBarAnimation() {
    tvBtnAuth.visibility = View.VISIBLE
    pbBtnAuth.visibility = View.GONE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}