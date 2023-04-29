package ar.edu.unlam.mobile2.fragmentQR

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun FragmentQR() {

    Content()

}

@Composable
private fun Content() {

    Box(modifier = Modifier.size(150.dp, 150.dp)) {

        Text(text = "SOY UN FRAGMENTO QR")
    }
}