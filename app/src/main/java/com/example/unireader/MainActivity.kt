package com.example.unireader

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.barteksc.pdfviewer.PDFView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PdfViewerScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewerScreen() {
    var pdfUri by remember { mutableStateOf<Uri?>(null) }

    // Selector de documentos para elegir PDF
    val pickPdf = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) pdfUri = uri
    }

    Scaffold(
        topBar = { SmallTopAppBar(title = { Text("Lector PDF") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { pickPdf.launch(arrayOf("application/pdf")) }) {
                Text("PDF")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (pdfUri == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Pulsa \"PDF\" para elegir un archivo.")
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { pickPdf.launch(arrayOf("application/pdf")) }) {
                        Text("Elegir PDF")
                    }
                }
            } else {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx -> PDFView(ctx, null) },
                    update = { view ->
                        view.fromUri(pdfUri)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .spacing(8)
                            .enableDoubletap(true)
                            .load()
                    }
                )
            }
        }
    }
}
