package top.yukonga.miuixlpa.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.ListPopupDefaults
import top.yukonga.miuix.kmp.basic.PopupPositionProvider
import top.yukonga.miuix.kmp.extra.DropdownImpl
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.dismissPopup

@Composable
fun ESimCard(
    cardName: String,
    status: Boolean,
    provider: String,
    clazz: String,
    iccid: String
) {
    CardView(
        insideMargin = PaddingValues(12.dp)
    ) {
        val statusString = if (status) "已激活" else "未激活"
        val isTopPopupExpanded = remember { mutableStateOf(false) }
        val showTopPopup = remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoText(
                title = cardName,
                content = "状态：$statusString\n供应商：$provider\n类型：$clazz\nICCID: $iccid"
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    isTopPopupExpanded.value = true
                },
                holdDownState = isTopPopupExpanded.value
            ) {
                if (isTopPopupExpanded.value) {
                    ListPopup(
                        show = showTopPopup,
                        popupPositionProvider = ListPopupDefaults.ContextMenuPositionProvider,
                        alignment = PopupPositionProvider.Align.TopRight,
                        onDismissRequest = {
                            isTopPopupExpanded.value = false
                        }
                    ) {
                        ListPopupColumn {
                            if (!status) {
                                DropdownImpl(
                                    text = "启用",
                                    optionSize = 3,
                                    isSelected = false,
                                    onSelectedIndexChange = {
                                        // TODO
                                        dismissPopup(showTopPopup)
                                        isTopPopupExpanded.value = false
                                    },
                                    index = 0
                                )
                                DropdownImpl(
                                    text = "删除",
                                    optionSize = 3,
                                    isSelected = false,
                                    onSelectedIndexChange = {
                                        // TODO
                                        dismissPopup(showTopPopup)
                                        isTopPopupExpanded.value = false
                                    },
                                    index = 1
                                )
                            }
                            DropdownImpl(
                                text = "重命名",
                                optionSize = if (!status) 3 else 1,
                                isSelected = false,
                                onSelectedIndexChange = {
                                    // TODO
                                    dismissPopup(showTopPopup)
                                    isTopPopupExpanded.value = false
                                },
                                index = if (!status) 2 else 0
                            )
                        }
                    }
                    showTopPopup.value = true
                }
                Icon(
                    imageVector = MiuixIcons.Useful.ImmersionMore,
                    tint = MiuixTheme.colorScheme.onBackground,
                    contentDescription = "More"
                )
            }
        }
    }
}
