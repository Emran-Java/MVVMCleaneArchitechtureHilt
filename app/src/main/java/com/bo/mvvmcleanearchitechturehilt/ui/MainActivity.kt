package com.bo.mvvmcleanearchitechturehilt.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bo.mvvmcleanearchitechturehilt.R
import com.bo.mvvmcleanearchitechturehilt.databinding.ActivityMainBinding
import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupInitialUI()

        callInitialApi()

        observeInitialStateChange()

        setUI()
    }

    private fun setUI() {

    }

    private fun setupInitialUI() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun observeInitialStateChange() {

        lifecycleScope.launch{
            viewModel.productList2.collectLatest {
                Log.d("apiCall","Size: ${it?.products?.size} Data: ${it.toString()}")
                if((it?.products?.size ?: 0) > 0){
                    binding.tvMonitor.text = "Data size: ${it?.products?.size}"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loaderState.collectLatest {
                binding.pbLoader.visibility = View.GONE
                if(it){
                    binding.pbLoader.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun callInitialApi() {

        viewModel.getProductList2()

    }

    private fun initialProductList() {

        viewModel.getProductList()
        lifecycleScope.launch {
            viewModel.productList.collectLatest {
                try{
                    when (it) {
                        is DataState.Loading -> {
                            Log.d("apiCall","Loading....")
                        }
                        is DataState.Error -> {
                            Log.d("apiCall","error: ${it.errorCode}, ${it.errorMessage}")
                        }
                        is DataState.Success -> {

                            Log.d("apiCall","success")

                            it.data.let {response ->
                                var products = response

                                Log.d("apiCall","Data: ${products.toString()}")
                            }
                        }
                        else -> {
                            Log.d("apiCall","else case")
                        }
                    }
                }catch (ex:Exception){
                    Log.d("apiCall","ex: ${ex.toString()}")
                }
            }
            /*viewModel.sendEmailState.collectLatest {
                when (it) {
                    is DataState.Loading -> {
                        showProgressBar()
                    }

                    is DataState.Error -> {
                        hideProgressBar()

                        EventBody(
                            eventName = "ReplaceEmailApiError",
                            code = it.errorCode,
                            message = it.errorMessage
                        ).submitError()

                        showToast(it.errorMessage)
                    }

                    is DataState.Success -> {
                        hideProgressBar()

                        it.data.let {
                            onEmailSent(it)
                        }
                    }

                    else -> {
                        hideProgressBar()
                    }
                }
            }*/
        }

    }
}