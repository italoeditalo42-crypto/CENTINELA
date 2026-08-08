package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/**
 * Input con estado local (clave = [itemKey]) que solo se resincroniza con
 * [initialValue] cuando cambia la clave — evita el salto de cursor típico
 * cuando el guardado en Room reemite el Flow mientras el usuario escribe.
 */
@Composable
fun SpTextField(
    itemKey: Any,
    initialValue: String,
    onCommit: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    minLines: Int = 1,
    style: TextStyle = SpType.body,
    ghost: Boolean = false,
) {
    var value by remember(itemKey) { mutableStateOf(initialValue) }
    val accent = spAccent()
    BasicTextField(
        value = value,
        onValueChange = { value = it; onCommit(it) },
        textStyle = style.copy(color = SpInk0),
        cursorBrush = SolidColor(accent.base),
        minLines = minLines,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (ghost) Modifier
                else Modifier
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0x40000000))
                    .border(1.dp, SpInk2.copy(alpha = 0.30f), RoundedCornerShape(3.dp))
            )
            .padding(if (ghost) PaddingValues(vertical = 6.dp) else PaddingValues(horizontal = 12.dp, vertical = 9.dp)),
        decorationBox = { inner ->
            if (value.isEmpty()) {
                Text(placeholder, style = style, color = SpInk2.copy(alpha = 0.6f))
            }
            inner()
        },
    )
}
