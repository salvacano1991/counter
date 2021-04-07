package com.salvacano.counterstuff.presentation.login.view

import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.salvacano.counterstuff.R
import com.salvacano.counterstuff.databinding.ActivityLoginBinding
import com.salvacano.counterstuff.presentation.common.view.BaseFragment
import com.salvacano.counterstuff.presentation.common.view.dialog.DialogProvider
import com.salvacano.counterstuff.presentation.login.viewmodel.LoginViewModel

import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment: BaseFragment<LoginViewModel, ActivityLoginBinding>() {

    companion object {
        const val RC_SIGN_IN = 0x001
    }

    override val viewModel by viewModel<LoginViewModel>()
    override val layoutId = R.layout.activity_login
    override val toolbarRes = R.id.toolbar
    override var showBackArrow: Boolean = false

    private lateinit var auth: FirebaseAuth

    override fun ActivityLoginBinding.initialize() {
        viewModel = this@LoginFragment.viewModel
        skipLogin.paintFlags = skipLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }

    override fun initialize() {
        viewModel.checkVersion()
        viewModel.isOldVersion.observe {
            if (it) {
                DialogProvider().getDownloadNewVersionDialog(requireActivity(), requireActivity().supportFragmentManager) { requireActivity().finish() }
            } else {
                val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
                if (account != null) {
                    goToCounters()
                } else {
                    toolbar.visibility = View.VISIBLE
                    googleLogin.visibility = View.VISIBLE
                    skipLogin.visibility = View.VISIBLE

                    auth = FirebaseAuth.getInstance()
                    viewModel.requestLoginSkip.observe {
                        goToCounters()
                    }
                    viewModel.requestGoogleLogin.observe {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .requestEmail().build()
                        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
                        val googleSignInIntent = googleSignInClient.signInIntent
                        startActivityForResult(googleSignInIntent, RC_SIGN_IN)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val user = response.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(user?.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        goToCounters(true)
                    } else {
                        showMessage(container, R.string.google_login_failed)
                    }
                }
            } catch (e: ApiException) {
                showMessage(container, R.string.google_login_failed)
            }
        }
    }


    private fun goToCounters(justLoggedWithGoogle: Boolean = false) {

    }
}