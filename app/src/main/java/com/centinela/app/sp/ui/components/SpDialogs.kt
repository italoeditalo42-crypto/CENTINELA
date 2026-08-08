package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.centinela.app.sp.ui.theme.SpBg0
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/** Equivalente a prompt() del navegador: pide un texto corto y confirma. */
@Composable
fun SpPromptDialog(
    title: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    placeholder: String = "",
    confirmLabel: String = "Crear",
) {
    var text by remember { mutableStateOf("") }
    val accent = spAccent()
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SpBg0)
                .border(1.dp, accent.base.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                .padding(20.dp),
        ) {
            Text(title.uppercase(), style = SpType.panelTitle, color = accent.base)
            Spacer(Modifier.height(14.dp))
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color(0x40000000))
                    .border(1.dp, SpInk2.copy(alpha = 0.35f), RoundedCornerShape(3.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            ) {
                if (text.isEmpty()) Text(placeholder, style = SpType.body, color = SpInk2.copy(alpha = 0.6f))
                BasicTextField(
                    value = text, onValueChange = { text = it },
                    textStyle = SpType.body.copy(color = SpInk0),
                    cursorBrush = SolidColor(accent.base),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End) {
                GlowButton(text = "Cancelar", onClick = onDismiss, style = SpButtonStyle.Outline)
                Spacer(Modifier.padding(end = 10.dp))
                GlowButton(
                    text = confirmLabel,
                    onClick = { if (text.isNotBlank()) onConfirm(text.trim()) },
                    style = SpButtonStyle.Solid,
                )
            }
        }
    }
}

/** Equivalente a confirmAction() del navegador: confirmar accion destructiva. */
@Composable
fun SpConfirmDialog(
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmLabel: String = "Eliminar",
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(SpBg0)
                .border(1.dp, Color(0xFFFF4526).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                .padding(20.dp),
        ) {
            Text("CONFIRMAR", style = SpType.panelTitle, color = Color(0xFFFF4526))
            Spacer(Modifier.height(10.dp))
            Text(message, style = SpType.body, color = SpInk1)
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End) {
                GlowButton(text = "Cancelar", onClick = onDismiss, style = SpButtonStyle.Outline)
                Spacer(Modifier.padding(end = 10.dp))
                GlowButton(text = confirmLabel, onClick = onConfirm, style = SpButtonStyle.Danger)
            }
        }
    }
}
