package com.bo.mvvmcleanearchitechturehilt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bo.mvvmcleanearchitechturehilt.data.model.Products
import com.bo.networkoperationdemo.data.model.pigeon_medicine.PigeonMonthlyMedicineCourseApiModel
import com.bo.mvvmcleanearchitechturehilt.network.network_data.DataState
import com.bo.mvvmcleanearchitechturehilt.data.repo.ProductsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Android Devs Academy (Ahmed Guedmioui)
 */

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val productsRepository: ProductsRepositoryImpl,
) : ViewModel() {
    /*init {
        viewModelScope.launch {
            productsRepository.getProductsList().collectLatest { result ->
                _sendEmailState.value = result as DataState<Products>
            }
        }
    }*/

    private val _productList: MutableStateFlow<DataState<Products>?> =
        MutableStateFlow(null)
    val productList = _productList.asStateFlow()

    fun getProductList() {
        viewModelScope.launch {
            productsRepository.getProductsList().collectLatest { result ->
                _productList.value = result as DataState<Products>
            }
        }
    }

    private val _productList2: MutableStateFlow<Products?> =
        MutableStateFlow(null)
    val productList2 = _productList2.asStateFlow()

    private val _loaderState: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val loaderState = _loaderState.asStateFlow()

    fun getProductList2() {
        viewModelScope.launch {
            productsRepository.getProductsList2().collectLatest { result ->
                //_productList2.value = result as Products
                try {
                    when (result) {
                        is DataState.Loading -> {
                            Log.d("apiCall","Loading....")
                            _loaderState.value = true
                        }
                        is DataState.Error -> {
                            _loaderState.value = false
                            Log.d("apiCall","error: ${result.errorCode}, ${result.errorMessage}")
                        }
                        is DataState.Success -> {
                            _loaderState.value = false
                            Log.d("apiCall","success")

                            result.data.let {response ->
                                Log.d("apiCall","Data: ${response.toString()}")
                                _productList2.value = response as Products
                                var products = response
                                Log.d("apiCall","Data: ${products.toString()}")
                            }
                        }
                        else -> {
                            _loaderState.value = false
                            Log.d("apiCall","else case")
                        }
                    }
                } catch (ex: Exception) {
                    _loaderState.value = false
                    Log.d("apiCall","ex: ${ex.toString()}")
                }
            }
        }
    }

    private val _medicineNameList: MutableStateFlow<DataState<PigeonMonthlyMedicineCourseApiModel>?> =
        MutableStateFlow(null)
    val medicineNameList = _medicineNameList.asStateFlow()

    fun getMedicineNames() {
        viewModelScope.launch {
            productsRepository.getMedicineName().collectLatest {
                _medicineNameList.value = it as DataState<PigeonMonthlyMedicineCourseApiModel>
            }
        }
    }

}