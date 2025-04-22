package top.yukonga.miuixlpa

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import kotlinx.coroutines.delay
import top.yukonga.miuix.kmp.basic.FloatingActionButton
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.ListPopup
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.ListPopupDefaults
import top.yukonga.miuix.kmp.basic.MiuixScrollBehavior
import top.yukonga.miuix.kmp.basic.PopupPositionProvider
import top.yukonga.miuix.kmp.basic.PullToRefresh
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.basic.rememberPullToRefreshState
import top.yukonga.miuix.kmp.basic.rememberTopAppBarState
import top.yukonga.miuix.kmp.extra.DropdownImpl
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.icons.useful.ImmersionMore
import top.yukonga.miuix.kmp.icon.icons.useful.New
import top.yukonga.miuix.kmp.icon.icons.useful.Refresh
import top.yukonga.miuix.kmp.theme.MiuixTheme.colorScheme
import top.yukonga.miuix.kmp.utils.MiuixPopupUtils.Companion.dismissPopup
import top.yukonga.miuix.kmp.utils.getWindowSize
import top.yukonga.miuix.kmp.utils.overScrollVertical
import top.yukonga.miuixlpa.ui.component.ESimCard
import top.yukonga.miuixlpa.ui.component.MainCard
import top.yukonga.miuixlpa.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        rememberCoroutineScope()
        val scrollBehavior = MiuixScrollBehavior(rememberTopAppBarState())

        val hazeState = remember { HazeState() }
        val hazeStyle = HazeStyle(
            backgroundColor = if (scrollBehavior.state.heightOffset > -1) Color.Transparent else colorScheme.background,
            tint = HazeTint(
                colorScheme.background.copy(
                    if (scrollBehavior.state.heightOffset > -1) 1f
                    else lerp(1f, 0.67f, (scrollBehavior.state.heightOffset + 1) / -143f)
                )
            )
        )
        val isTopPopupExpanded = remember { mutableStateOf(false) }
        val showTopPopup = remember { mutableStateOf(false) }

        val pullToRefreshState = rememberPullToRefreshState()
        var isRefreshing by remember { mutableStateOf(false) }
        var ii = remember { mutableIntStateOf(2) }

        LaunchedEffect(pullToRefreshState.isRefreshing) {
            if (pullToRefreshState.isRefreshing) {
                isRefreshing = true
                delay(300)
                pullToRefreshState.completeRefreshing {
                    isRefreshing = false
                }
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    color = Color.Transparent,
                    modifier = Modifier.hazeEffect(
                        state = hazeState,
                        style = hazeStyle
                    ),
                    title = stringResource(R.string.app_name),
                    scrollBehavior = scrollBehavior,
                    actions = {
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
                                    DropdownImpl(
                                        text = "设置",
                                        optionSize = 5,
                                        isSelected = false,
                                        onSelectedIndexChange = {
                                            // TODO
                                            dismissPopup(showTopPopup)
                                            isTopPopupExpanded.value = false
                                        },
                                        index = 0
                                    )
                                    DropdownImpl(
                                        text = "兼容性检查",
                                        optionSize = 5,
                                        isSelected = false,
                                        onSelectedIndexChange = {
                                            // TODO
                                            dismissPopup(showTopPopup)
                                            isTopPopupExpanded.value = false
                                        },
                                        index = 1
                                    )
                                    DropdownImpl(
                                        text = "通知管理",
                                        optionSize = 5,
                                        isSelected = false,
                                        onSelectedIndexChange = {
                                            // TODO
                                            dismissPopup(showTopPopup)
                                            isTopPopupExpanded.value = false
                                        },
                                        index = 2
                                    )
                                    DropdownImpl(
                                        text = "eUICC 详情",
                                        optionSize = 5,
                                        isSelected = false,
                                        onSelectedIndexChange = {
                                            // TODO
                                            dismissPopup(showTopPopup)
                                            isTopPopupExpanded.value = false
                                        },
                                        index = 3
                                    )
                                    DropdownImpl(
                                        text = "打开 SIM 卡应用程序",
                                        optionSize = 5,
                                        isSelected = false,
                                        onSelectedIndexChange = {
                                            // TODO
                                            dismissPopup(showTopPopup)
                                            isTopPopupExpanded.value = false
                                        },
                                        index = 4
                                    )
                                }
                            }
                            showTopPopup.value = true
                        }
                        IconButton(
                            modifier = Modifier.padding(end = 2.dp),
                            onClick = {
                                // TODO
                                ii.intValue++
                            }
                        ) {
                            Icon(
                                imageVector = MiuixIcons.Useful.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                        IconButton(
                            modifier = Modifier.padding(end = 21.dp),
                            onClick = {
                                isTopPopupExpanded.value = true
                            },
                            holdDownState = isTopPopupExpanded.value
                        ) {
                            Icon(
                                imageVector = MiuixIcons.Useful.ImmersionMore,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom)),
                    onClick = {
                        // TODO
                    }
                ) {
                    Icon(
                        imageVector = MiuixIcons.Useful.New,
                        tint = Color.White,
                        contentDescription = "Add"
                    )
                }
            }
        ) { padding ->
            PullToRefresh(
                modifier = Modifier.padding(
                    top = padding.calculateTopPadding()
                ),
                pullToRefreshState = pullToRefreshState,
                onRefresh = {
                    // TODO
                    ii.intValue++
                }
            ) {
                LazyColumn(
                    modifier = Modifier
                        .hazeSource(state = hazeState)
                        .height(getWindowSize().height.dp)
                        .overScrollVertical()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) {
                    item {
                        Spacer(Modifier.height(12.dp))
                        MainCard()
                        for (i in 0 until ii.intValue) {
                            ESimCard(
                                cardName = "eSim Card ${1 + i}",
                                status = i == 0,
                                provider = "移不动",
                                clazz = "可用",
                                iccid = (8986000000000000000 + i).toString()
                            )
                        }
                        Spacer(Modifier.height(12.dp + padding.calculateBottomPadding()))
                    }
                }
            }
        }
    }
}