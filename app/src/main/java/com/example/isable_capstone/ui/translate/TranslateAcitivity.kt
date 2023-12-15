package com.example.isable_capstone.ui.translate

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.isable_capstone.R
import com.example.isable_capstone.databinding.ActivityTranslateAcitivityBinding
import com.example.isable_capstone.ml.SignLanguageModelV3Rgb
import com.example.isable_capstone.ml.SignLanguageModelV4Rgb
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class TranslateAcitivity : AppCompatActivity() {

    private lateinit var binding : ActivityTranslateAcitivityBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var tvOutput : TextView
    private lateinit var buttonLoad : Button
    private val GALLERY_REQUEST_CODE=123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateAcitivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ==PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "iamge/*"
                val mimeTypes = arrayOf("image/jpeg","image/png","image/jpg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onResult.launch(intent)
            }
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

    //get image from gallery
    private val onResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        Log.i("TAG","This is the result : ${result.data} ${result.resultCode}")
        onResultReceived(GALLERY_REQUEST_CODE,result)
    }

    private fun onResultReceived(requestCode:Int,result: ActivityResult?){
        when (requestCode){
            GALLERY_REQUEST_CODE->{
                if(result?.resultCode== Activity.RESULT_OK){
                    result.data?.data?.let { uri->
                        Log.i("TAG","onResultReceived: $uri")
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        imageView.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                }else{
                    Log.e("TAG","onActivityResult: error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap){
        //declearing tensorflow lite model variable

        val model = SignLanguageModelV4Rgb.newInstance(this)

        // Resize the bitmap to match the input size expected by the model
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 244, 244, true)

        // Convert the Bitmap to a ByteBuffer
        val inputBuffer = ByteBuffer.allocateDirect(1 * 244 * 244 * 3 * 4)
        inputBuffer.order(java.nio.ByteOrder.nativeOrder())

        // Rewind the buffer before use
        inputBuffer.rewind()

        val intValues = IntArray(244 * 244)
        resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

        for (i in 0 until 244) {
            for (j in 0 until 244) {
                val `val` = intValues[i * 244 + j]
                inputBuffer.putFloat(((`val` shr 16 and 0xFF) / 255.0).toFloat())
                inputBuffer.putFloat(((`val` shr 8 and 0xFF) / 255.0).toFloat())
                inputBuffer.putFloat(((`val` and 0xFF) / 255.0).toFloat())
            }
        }

        // Rewind the buffer before use
        inputBuffer.rewind()

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 244, 244, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(inputBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(
            inputFeature0
        )
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        // Dapatkan indeks dengan nilai tertinggi (kelas prediksi)
        val maxIndex = outputFeature0.floatArray.indices.maxBy { outputFeature0.floatArray[it] } ?: -1

        // Pastikan bahwa indeks adalah valid dan bukan -1
        val classes = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        val predictedClass = if(maxIndex != -1) classes[maxIndex] else "Unknown"

        // Tampilkan prediksi kelas dan probabilitas
        tvOutput.text = "Model Output: ${predictedClass}, \nProbability: ${outputFeature0.floatArray[maxIndex]}"



//        // Do something with the output, for example, update a TextView
//        tvOutput.text = "Model Output: \nFloatArray: ${outputFeature0.floatArray[0]}"+"\nShape: ${outputFeature0.shape}"+"\ntypeSize: ${outputFeature0.typeSize}"+"\n" +
//                "flatSize: ${outputFeature0.flatSize}"+"\nbuffer: ${outputFeature0.buffer}"
//
//        // Releases model resources if no longer used.
//        model.close()
    }
}