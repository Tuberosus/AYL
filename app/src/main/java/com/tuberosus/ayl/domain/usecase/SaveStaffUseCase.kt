package com.tuberosus.ayl.domain.usecase

import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.domain.repository.StaffRepository

class SaveStaffUseCase(
    private val staffRepository: StaffRepository
) {
    suspend operator fun invoke(staff: Staff) = staffRepository.saveStaff(staff)
}