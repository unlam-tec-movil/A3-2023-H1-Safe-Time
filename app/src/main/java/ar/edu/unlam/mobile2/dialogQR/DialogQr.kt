package ar.edu.unlam.mobile2.dialogQR

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter


@Composable
fun QRDialog(info: String, onDismiss: () -> Unit) {
    val qrCode = QRCodeWriter().encode(info, BarcodeFormat.QR_CODE, 512, 512)
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        title = { Text(text = "Código QR") },
        text = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .heightIn(max = 512.dp)
                    .wrapContentHeight(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Escanea el siguiente código QR para ver la información:")
                Canvas(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(bottom = 30.dp)
                ) {
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics.Paint().apply {
                            color = Color.Black.toArgb()
                        }
                        qrCode.run {
                            val width = width
                            val height = height
                            val pixels = IntArray(width * height)
                            for (y in 0 until height) {
                                val offset = y * width
                                for (x in 0 until width) {
                                    pixels[offset + x] = if (get(x, y)) {
                                        Color.Black.toArgb()
                                    } else {
                                        Color.White.toArgb()
                                    }
                                }
                            }
                            canvas.nativeCanvas.drawBitmap(
                                pixels,
                                0,
                                width,
                                0f,
                                0f,
                                width,
                                height,
                                false,
                                paint
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cerrar")
            }
        }
    )
}