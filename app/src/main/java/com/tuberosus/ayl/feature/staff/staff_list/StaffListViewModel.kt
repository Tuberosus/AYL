package com.tuberosus.ayl.feature.staff.staff_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuberosus.ayl.domain.repository.StaffRepository
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
            val staff = staffRepository.getStaff()
            _state.update {
                it.copy(
                    isLoading = false,
                    staff = staff
                )
            }
        }
    }
}