@file:Suppress("DEPRECATION")

package Vistas.Componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun sideBar(selectedItem: String, onItemSelected: (String) -> Unit) {
    var expandedMenu by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .background(Color(0xFF1E1E1E))
            .padding(vertical = 8.dp)

    ) {
        SideBarLogo()
        Divider(color = Color(0xFF3E3E3E), thickness = 1.dp, modifier = Modifier.padding(bottom = 20.dp))

        sideBarMenuItem("Inicio", Icons.Default.Home, selectedItem, onItemSelected)


        sideBarMenuItem("Tablero", Icons.Default.EventNote, selectedItem, onItemSelected)


        sideBarMenuGroup("Reportes", Icons.Default.Assessment, expandedMenu) {
            expandedMenu = if (expandedMenu == "Reportes") "" else "Reportes"
        }
        if (expandedMenu == "Reportes") {
            sideBarSubMenuItem("Reporte 1", selectedItem, onItemSelected)
            sideBarSubMenuItem("Reporte 2", selectedItem, onItemSelected)
        }

        Spacer(modifier = Modifier.weight(1f))

        sideBarMenuItem("Salir", Icons.AutoMirrored.Filled.Logout, selectedItem, onItemSelected)

    }
}

@Composable
fun SideBarLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
            painter = painterResource("FuturamaBender.png"),
            contentDescription = "Logo",
            modifier = Modifier.size(60.dp)
        )

        Spacer(Modifier.width(12.dp))
        Text(
            "Robot Curioso",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun sideBarMenuItem(text: String, icon: ImageVector, selectedItem: String, onItemSelected: (String) -> Unit) {
    val isSelected = selectedItem == text
    var isHovered by remember { mutableStateOf(false) }

    val backgroundColor = when {
        isSelected -> Color(16, 78, 146)
        isHovered -> Color(0xFF2A2A2A)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { onItemSelected(text) }
            .hoverable(
                interactionSource = remember { MutableInteractionSource() },

                enabled = true,

                )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = Color.White, fontSize = 14.sp)
    }
}

@Composable
fun sideBarMenuGroup(text: String, icon: ImageVector, expandedMenu: String, onToggle: () -> Unit) {
    val isExpanded = expandedMenu == text
    var isHovered by remember { mutableStateOf(false) }

    val backgroundColor = if (isHovered) Color(0xFF2A2A2A) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onToggle)
            .hoverable(
                interactionSource = remember { MutableInteractionSource() },

                enabled = true,

                )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = Color.White, fontSize = 14.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun sideBarSubMenuItem(text: String, selectedItem: String, onItemSelected: (String) -> Unit) {
    val isSelected = selectedItem == text
    var isHovered by remember { mutableStateOf(false) }

    val backgroundColor = when {
        isSelected -> Color(16, 78, 146)
        isHovered -> Color(0xFF2A2A2A)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .clickable { onItemSelected(text) }
            .hoverable(
                interactionSource = remember { MutableInteractionSource() },

                enabled = true,

                )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}