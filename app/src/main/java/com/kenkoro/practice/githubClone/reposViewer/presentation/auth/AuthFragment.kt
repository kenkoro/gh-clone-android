package com.kenkoro.practice.githubClone.reposViewer.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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
    btnAuth.setOnClickListener {
      val actions = authViewModel.onSignInButtonPressed()
      viewLifecycleOwner.lifecycleScope.launch {
        actions.collectLatest { action ->
          when (action) {
            AuthViewModel.Action.RouteToMain -> {
              Log.d("kenkoro", "Go to the main screen")
            }

            is AuthViewModel.Action.ShowError -> {
              Log.d("kenkoro", "Error: ${action.message}")
            }
          }
        }
      }
    }
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
        AuthViewModel.State.Idle -> {
          // TODO: Refactor these animation moments
          if (pbBtnAuth.isVisible) {
            stopProgressBarAnimation()
          }
        }

        is AuthViewModel.State.InvalidInput -> {
          if (!tilAuthToken.hasFocus()) {
            tilAuthToken.requestFocus()
          }
          tilAuthToken.error = ContextCompat.getString(requireContext(), R.string.auth_et_helper)
          if (pbBtnAuth.isVisible) {
            stopProgressBarAnimation()
          }
        }

        AuthViewModel.State.Loading -> {
          if (!pbBtnAuth.isVisible) {
            startProgressBarAnimation()
          }
        }
      }
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