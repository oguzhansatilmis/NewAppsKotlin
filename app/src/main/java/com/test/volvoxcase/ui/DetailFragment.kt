package com.test.volvoxcase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewFragment
import androidx.core.view.doOnPreDraw
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.test.volvoxcase.R
import com.test.volvoxcase.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding:FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
       binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.let {url->
            println("detail fragment")
            println(url)
            binding.detailWebview.loadUrl(url.url.toString())
        }

        (view.parent as? ViewGroup)?.doOnPreDraw {
            // Parent has been drawn. Start transitioning!
            startPostponedEnterTransition()
        }
    }
}