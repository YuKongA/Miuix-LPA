package top.yukonga.miuixlpa.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardDefaults
import top.yukonga.miuix.kmp.basic.LinearProgressIndicator
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.utils.SmoothRoundedCornerShape
import java.util.Locale

@Composable
fun MainCard(
    cardSize: Int = 5, // 已写入的卡片数量
    activeCardName: String = "eSim Card 1", // 已激活的卡名
    totalCapacity: Int = 102400, // 总容量
    remainingCapacity: Int = 40960, // 剩余容量
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 6.dp, bottom = 12.dp)
                .fillMaxHeight()
                .clip(SmoothRoundedCornerShape(CardDefaults.CornerRadius)),
            insideMargin = PaddingValues(12.dp)
        ) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = "当前激活：",
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = activeCardName,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp, end = 12.dp, bottom = 12.dp)
                .clip(SmoothRoundedCornerShape(CardDefaults.CornerRadius)),
            insideMargin = PaddingValues(12.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "统计信息：",
                    fontWeight = FontWeight.Medium
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    StatisticItem(
                        title = "已写入",
                        value = cardSize.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    StatisticItem(
                        title = "总容量",
                        value = formatCapacity(totalCapacity),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    StatisticItem(
                        title = "剩余容量",
                        value = formatCapacity(remainingCapacity),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                val usedCapacity = totalCapacity - remainingCapacity
                LinearProgressIndicator(
                    progress = if (totalCapacity > 0) usedCapacity.toFloat() / totalCapacity else 0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 6.dp)
                        .height(4.dp)
                )
            }
        }
    }
}

fun formatCapacity(capacityKiB: Int): String {
    return if (capacityKiB >= 1024) {
        String.format(Locale.ENGLISH, "%.1f MiB", capacityKiB / 1024f)
    } else {
        "$capacityKiB KiB"
    }
}

@Composable
fun StatisticItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    isCardName: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = MiuixTheme.textStyles.body2.fontSize
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium,
            fontSize = MiuixTheme.textStyles.body2.fontSize,
            maxLines = if (isCardName) 1 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}