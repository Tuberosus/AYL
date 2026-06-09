package com.tuberosus.ayl.ui.components.bottomsheets

import androidx.compose.runtime.Composable
import com.tuberosus.ayl.domain.model.gallery.GalleryPhoto
import com.tuberosus.ayl.domain.model.news.News
import com.tuberosus.ayl.domain.model.staff.Staff
import com.tuberosus.ayl.feature.admin.AuthAction
import com.tuberosus.ayl.feature.admin.AuthScreenRoot
import com.tuberosus.ayl.feature.admin.AuthViewModel
import com.tuberosus.ayl.feature.gallery.gallery_admin.GalleryAdminRoot
import com.tuberosus.ayl.feature.news.news_admin.NewsAdminScreenRoot
import com.tuberosus.ayl.feature.staff.staff_admin.StaffAdminRoot

sealed interface BottomSheetType {
    data class GalleryType(val photo: GalleryPhoto? = null) : BottomSheetType
    data class NewsType(val news: News? = null) : BottomSheetType
    data class StaffType(val staff: Staff? = null) : BottomSheetType
    data object Auth : BottomSheetType
}

@Composable
fun AppRootBottomSheet(
    authViewModel: AuthViewModel,
    onTypeChange: (BottomSheetType?) -> Unit,
    showDialogType: BottomSheetType? = null
) {
    showDialogType?.let { type ->
        AdminBottomSheet(
            onDismiss = {
                authViewModel.onAction(AuthAction.ClearInput)
                onTypeChange(null)
            }
        ) {
            when (type) {
                is BottomSheetType.GalleryType ->
                    GalleryAdminRoot(
                        onDismiss = { onTypeChange(null) },
                    )

                is BottomSheetType.NewsType ->
                    NewsAdminScreenRoot(
                        newsForUpdate = type.news,
                        onDismiss = { onTypeChange(null) }
                    )

                is BottomSheetType.StaffType ->
                    StaffAdminRoot(
                        staffForUpdate = type.staff,
                        onDismiss = { onTypeChange(null) }
                    )

                BottomSheetType.Auth ->
                    AuthScreenRoot(
                        viewModel = authViewModel,
                        onDismiss = {
                            authViewModel.onAction(AuthAction.ClearInput)
                            onTypeChange(null)
                        }
                    )
            }
        }
    }
}