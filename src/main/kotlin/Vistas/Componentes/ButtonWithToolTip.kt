package Vistas.Componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyButtonWithTooltip(texto: String, textoAlternativo: String, onClick: () -> Unit) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(Color(0xFF1E88E5)),
            modifier = Modifier
                .size(180.dp, 50.dp)
                .onPointerEvent(PointerEventType.Move) {
                }
                .onPointerEvent(PointerEventType.Enter) {
                    showTooltip = true
                }
                .onPointerEvent(PointerEventType.Exit) {
                    showTooltip = false
                }
        ) {
            Text(
                text = texto,
                color = Color.White
            )
        }

        if (showTooltip) {
            Popup(
                alignment = Alignment.BottomCenter,
                offset = IntOffset(-40, -80),
                properties = PopupProperties(focusable = false),
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(37,50,76))
                        .padding(8.dp)
                        .width(250.dp)
                ) {
                    Text(
                        text = textoAlternativo,
                        color = Color.White,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}


