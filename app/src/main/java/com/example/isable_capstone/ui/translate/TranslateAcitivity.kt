package com.example.isable_capstone.ui.translate


import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.isable_capstone.databinding.ActivityTranslateAcitivityBinding
import com.example.isable_capstone.ml.SignLanguageModelV4RgbWithMetadata
import org.tensorflow.lite.support.image.TensorImage


class TranslateAcitivity : AppCompatActivity() {

    private lateinit var binding : ActivityTranslateAcitivityBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var tvOutput : TextView
    private lateinit var buttonLoad : Button

    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateAcitivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val toolbar = binding.xmlToolbar.root
        setSupportActionBar(toolbar)
        supportActionBar?.title="Translate"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        imageView= binding.ivPhoto
        button = binding.btnTakePhoto
        tvOutput = binding.tvOutput
        buttonLoad = binding.btnLoadImage

        button.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED){
                takePicturePreview.launch(null)
            }else{
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }
        }

        buttonLoad.setOnClickListener{
            startGallery()
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            Log.i("TAG","onResultReceived: $currentImageUri")
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(currentImageUri!!))
            imageView.setImageBitmap(bitmap)
            outputGenerator(bitmap)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    //request camera permission
    private  val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){granted->
        if(granted){
            takePicturePreview.launch(null)
        }else{
            Toast.makeText(this,"Permission Denied!! Try Again", Toast.LENGTH_SHORT).show()
        }
    }

    //laucnh camera and take photo
    private val takePicturePreview = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){bitmap->
        if(bitmap != null){
            imageView.setImageBitmap(bitmap)
            outputGenerator(bitmap)
        }
    }


    private fun outputGenerator(bitmap: Bitmap){
        //declearing tensorflow lite model variable

        val model = SignLanguageModelV4RgbWithMetadata.newInstance(this)

        val input = TensorImage.fromBitmap(bitmap)

        // Runs model inference and gets result.
        val outputs = model.process(input)

// Dapatkan indeks dengan nilai tertinggi (kelas prediksi)
        val maxIndex = outputs.probabilityAsTensorBuffer.floatArray.indices.maxBy { outputs.probabilityAsTensorBuffer.floatArray[it] }?:-1

// Pastikan bahwa indeks adalah valid dan bukan -1
        val classes = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        val predictedClass = if(maxIndex != -1) classes[maxIndex] else "Unknown"

//        // Tampilkan prediksi kelas dan probabilitas
        tvOutput.text = "Prediction Result : ${predictedClass}, \nProbability: ${outputs.probabilityAsTensorBuffer.floatArray[maxIndex]}"


        // Releases model resources if no longer used.
        model.close()
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}