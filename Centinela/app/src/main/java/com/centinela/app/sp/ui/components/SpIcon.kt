package com.centinela.app.sp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    tint: androidx.compose.ui.graphics.Color = LocalContentColor.current,
) {
    Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = modifier.size(size))
}
