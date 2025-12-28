package com.example.textonly

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.Executors

class ScanQRActivity : ComponentActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var statusText: TextView
    private val executor = Executors.newSingleThreadExecutor()
    private val client = OkHttpClient()

    // 🔴 URL-ul Backend-ului de pe Render
    private val BACKEND_URL = "https://textonly-backend-web.onrender.com"

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) startCamera()
            else Toast.makeText(this, "Ai nevoie de acces la cameră!", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        previewView = findViewById(R.id.previewView)
        statusText = findViewById(R.id.txtStatus)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build()
            preview.setSurfaceProvider(previewView.surfaceProvider)

            val barcodeScanner = BarcodeScanning.getClient()
            val analysis = ImageAnalysis.Builder().build().also {
                it.setAnalyzer(executor) { imageProxy ->
                    processImageProxy(barcodeScanner, imageProxy)
                }
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            cameraProvider.bindToLifecycle(this, cameraSelector, preview, analysis)
        }, ContextCompat.getMainExecutor(this))
    }

    private var isProcessing = false

    private fun processImageProxy(scanner: com.google.mlkit.vision.barcode.BarcodeScanner, imageProxy: ImageProxy) {
        if (isProcessing) {
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val qrValue = barcode.rawValue
                        if (qrValue != null) {
                            isProcessing = true
                            runOnUiThread {
                                statusText.text = "Cod detectat..."
                            }
                            sendTokenToBackend(qrValue)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("QR", "Eroare: ${e.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        } else {
            imageProxy.close()
        }
    }

    private fun sendTokenToBackend(token: String) {
        // Obținem numărul de telefon salvat (sau folosim unul default pentru testare)
        val prefs: SharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val phoneNumber = prefs.getString("phoneNumber", "0712345678") ?: "0712345678"

        val json = JSONObject()
        json.put("token", token)
        json.put("phoneNumber", phoneNumber)

        Log.d("QR_LOGIN", "Sending: $json to $BACKEND_URL/api/auth/qr/validate")

        val requestBody = json.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url("$BACKEND_URL/api/auth/qr/validate")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@ScanQRActivity, "Eroare conexiune: ${e.message}", Toast.LENGTH_SHORT).show()
                    isProcessing = false
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ScanQRActivity, "Conectare Desktop Reușită! ✅", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@ScanQRActivity, "Eroare server: ${response.code}", Toast.LENGTH_SHORT).show()
                        isProcessing = false
                    }
                }
            }
        })
    }
}
