package edu.hm.cs.ma.todoguru.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import edu.hm.cs.ma.todoguru.R
import timber.log.Timber

class PrivacyPolicyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater
            .inflate(R.layout.privacy_policy_fragment, container, false)
            .apply {
                findViewById<WebView>(R.id.webView).apply {
                    loadUrl("file:///android_asset/privacy_policy.html")
                    Timber.d("Showing privacy policy message")
                }
            }
    }
}
