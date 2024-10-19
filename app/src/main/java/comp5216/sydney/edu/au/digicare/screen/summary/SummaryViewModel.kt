package comp5216.sydney.edu.au.digicare.screen.summary

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import comp5216.sydney.edu.au.digicare.model.service.AccountService
import comp5216.sydney.edu.au.digicare.model.service.GeminiIntegrationService
import comp5216.sydney.edu.au.digicare.model.service.StorageService
import comp5216.sydney.edu.au.digicare.screen.DigiCareViewModel
import comp5216.sydney.edu.au.digicare.util.combineRecords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
) : DigiCareViewModel() {

    var showDialog by mutableStateOf(false)
        private set

    private val _analysisResult = MutableStateFlow("")
    val analysisResult: StateFlow<String> = _analysisResult
    var startDate by mutableStateOf("Start Date")
    var endDate by mutableStateOf("End Date")

    private var latestResult: String = ""  // Store the latest result for later saving

    fun onGenerateClick(context: Context) {
        showDialog = true
        launchCatching {
            val records = storageService.fetchRecords(startDate, endDate, accountService.currentUserId)
            val combinedRecords = combineRecords(records)

            GeminiIntegrationService().sendToGemini(combinedRecords, context, object :
                GeminiIntegrationService.GeminiCallback {
                override fun onSuccess(result: String) {
                    latestResult = result // Save the result internally
                    _analysisResult.value = result // Show result to the user
                }

                override fun onFailure(error: String) {
                    _analysisResult.value = "Error: $error"
                }
            })
        }
    }

    // Save the summary as PDF only when the user clicks "Save"
    fun onSaveClick(context: Context) {
        saveSummaryAsPdf(context, latestResult) {
            Toast.makeText(context, "Summary saved successfully!", Toast.LENGTH_SHORT).show()
        }
        showDialog = false // Dismiss dialog after saving
    }

    private fun saveSummaryAsPdf(context: Context, summaryText: String, onComplete: () -> Unit) {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if (directory == null || (!directory.exists() && !directory.mkdirs())) {
            _analysisResult.value = "Error: Failed to access storage."
            return
        }

        val file = File(directory, "summary.pdf")
        val document = PdfDocument()
        var pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        var page = document.startPage(pageInfo)

        val paint = android.graphics.Paint().apply { textSize = 14f }
        val pageWidth = pageInfo.pageWidth - 40
        val pageHeight = pageInfo.pageHeight - 40
        var y = 25f

        val lines = summaryText.split("\n")
        for (line in lines) {
            var start = 0
            while (start < line.length) {
                val count = paint.breakText(line, start, line.length, true, pageWidth.toFloat(), null)
                val substring = line.substring(start, start + count)

                page.canvas.drawText(substring, 20f, y, paint)
                y += paint.textSize + 5
                start += count

                if (y + paint.textSize > pageHeight) {
                    document.finishPage(page)
                    pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
                    page = document.startPage(pageInfo)
                    y = 25f
                }
            }
        }

        document.finishPage(page)

        try {
            val fileOutputStream = FileOutputStream(file)
            document.writeTo(fileOutputStream)
            fileOutputStream.close()
            document.close()

            onComplete()
        } catch (e: IOException) {
            e.printStackTrace()
            _analysisResult.value = "Error saving PDF: ${e.message}"
        }
    }

    fun onViewPdfClick(context: Context) {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(directory, "summary.pdf")

        if (file.exists()) {
            openPdf(context, file)
        } else {
            Toast.makeText(context, "PDF not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openPdf(context: Context, file: File) {
        try {
            val uri = androidx.core.content.FileProvider.getUriForFile(
                context, "${context.packageName}.provider", file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "No PDF reader found or cannot open the PDF", Toast.LENGTH_SHORT).show()
        }
    }

    fun onCancelClick() {
        showDialog = false
    }

    fun updateStartDate(date: String) {
        startDate = date
    }

    fun updateEndDate(date: String) {
        endDate = date
    }
}
