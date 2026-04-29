package com.tuberosus.ayl.feature.staff.staff_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.StaffRepository
import com.tuberosus.ayl.domain.util.onFailure
import com.tuberosus.ayl.domain.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StaffListViewModel(
    private val staffRepository: StaffRepository
) : ViewModel() {
    private var _state = MutableStateFlow(StaffListState())
    val state = _state.asStateFlow()

    init {
        getStaff()
    }

    private fun getStaff() {
        viewModelScope.launch {
            staffRepository.getStaff()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            staff = result
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            staff = null,
                            error = error
                        )
                    }
                }
        }
    }
}