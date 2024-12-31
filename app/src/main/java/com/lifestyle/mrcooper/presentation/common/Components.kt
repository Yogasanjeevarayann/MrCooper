package com.lifestyle.mrcooper.presentation.common

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
    height: Dp = 60.dp,
    width: Dp = 150.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(height)
            .width(width)
    ) {
        Text(
            text = text,
            style = textStyle
        )
    }
}
