package com.example.newappproject

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import android.widget.*

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide

import com.example.newappproject.ApiConfigs.ApiClient
import com.example.newappproject.ApiConfigs.ApiService
import com.example.newappproject.LocalDatabase.AppDatabse
import com.example.newappproject.LocalDatabase.Entity.PremiumData
import com.example.newappproject.NetWorkHelper.NetworkCheck
import com.example.newappproject.Repository.RepositoryAll
import com.example.newappproject.ViewModel.MyDataViewModel
import com.example.newappproject.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var usedDatabase :AppDatabse
    lateinit var viewModel:MyDataViewModel
    lateinit var networkCheck:NetworkCheck
    lateinit var repositoryAll:RepositoryAll
    lateinit var imageview:ImageView
    lateinit var button:Button
    lateinit var closeimage:ImageView
    lateinit var downloadButton:LinearLayout
    lateinit var playButton:LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        instalValueFun()
        checkFirstInstal()
        createDialog()
        loadImage()
        checkPremiumData()
        longClickAction()
     saveImageToStorage()

        playVideo()


    }

    private fun saveImageToStorage() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        val cardView =findViewById<ImageView>(R.id.download_img)
        val captureButton = findViewById<LinearLayout>(R.id.download_btn)

        captureButton.setOnClickListener {
            val bitmap = getScreenShotFromView(cardView)

            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }
        }
    }
    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot:Bitmap?=null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(screenshot)
            v.draw(canvas)
        }catch (e:Exception){
            Log.e("DOWN","Failed to capture img because:"+e.message)
        }
        return screenshot
    }


    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            this.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "img_download/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }


                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)


                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {

            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }


    var TAG =  "@@@@@@"
    private fun playVideo() {
        var uri = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        var videoView  = binding.videoView

        videoView.setVideoURI(Uri.parse(uri))
        var controler  = MediaController(this)
        var position:Int = 0
        var checkerPlay: Int  =  0
        playButton.setOnClickListener{
            if(checkerPlay%2==0&&checkerPlay!=0){
                videoView.seekTo(position)
                videoView.start()
                Log.d(TAG, "playVideo: resume")
            }
            else if (checkerPlay%2==1&&checkerPlay!=0){
                Log.d(TAG, "playVideo:pausa ")
                videoView.pause()
                position =videoView.currentPosition
            }
            else{
                Log.d(TAG, "playVideo: start")
                videoView.start()
            }

            checkerPlay++

        }




    }


    private fun longClickAction() {

        longPressFun(binding.GrammerSheet)
        longPressFun(binding.HomeWorkSheet)
        longPressFun(binding.SpeakingSheet)
        longPressFun(binding.VocablarySheet)
        longPressFun(binding.ListeningSheet)
        tripleTapCheck(binding.Grammer)
        tripleTapCheck(binding.Speaking)
        tripleTapCheck(binding.Vocablary)
        tripleTapCheck(binding.HomeWork)
        tripleTapCheck(binding.Listening)


    }

    private fun checkPremiumData() {
        viewModel.getLocalData().observe(this, Observer {
            Log.d("@@@@@", "onCreate:${it.grammer} ")
            if(it.grammer==false){
                binding.GrammerSheet.visibility = View.VISIBLE
            }
            else{
                binding.GrammerSheet.visibility = View.GONE
            }
            if(it.homework==false){
                binding.HomeWorkSheet.visibility = View.VISIBLE
            }
            else{
                binding.HomeWorkSheet.visibility = View.GONE
            }
            if(it.spiking==false){
                binding.SpeakingSheet.visibility = View.VISIBLE

            }
            else{
                binding.SpeakingSheet.visibility = View.GONE
            }
            if(it.listining==false){
                binding.ListeningSheet.visibility = View.VISIBLE
            }
            else{
                binding.ListeningSheet.visibility  = View.GONE
            }
            if(it.vocablary==false){
                binding.VocablarySheet.visibility = View.VISIBLE
            }
            else{
                binding.VocablarySheet.visibility  = View.GONE
            }
        })
    }

    private fun loadImage() {
        viewModel.getRemotedaFun().observe(this, Observer {
          //  Glide.with(this).load(it.urls.small).centerCrop().into(imageview)
            Glide.with(this).load(it.urls.small).centerCrop().into(imageview)
        })
    }

    private fun instalValueFun(){
        downloadButton = binding.downloadBtn
        playButton =  binding.playButton

        networkCheck  = NetworkCheck()
        var apiClient  = ApiClient.getClient().create(ApiService::class.java)
        usedDatabase   = AppDatabse.getInstance(this)!!
        repositoryAll= RepositoryAll(apiClient,usedDatabase!!)

        viewModel = MyDataViewModel(networkCheck,repositoryAll)

    }
    private fun createDialog(){
        var dialog  = AlertDialog.Builder(this)
        var view  = LayoutInflater.from(this).inflate(R.layout.dialogs_layout,null,false)
        imageview  = view.findViewById(R.id.dialog_imageview)
        button = view.findViewById(R.id.dialog_button)
        closeimage =  view.findViewById(R.id.imageView_cancel)

        dialog.setView(view)
        var alertDialog:AlertDialog  = dialog.create()
        alertDialog.show()
        button.setOnClickListener{
            alertDialog.dismiss()
        }
        closeimage.setOnClickListener{
            alertDialog.dismiss()
        }
    }

    private fun checkFirstInstal() {
        if(usedDatabase.getUserDao().getPremiumData().isEmpty()){
           viewModel.insertDataFirstTime(PremiumData(null))
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    fun longPressFun(linearLayout: LinearLayout):Boolean{
        val longClickDuration = 5000
        var isLongPress = false

        linearLayout.setOnTouchListener(OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                isLongPress = true
                val handler = Handler()
                handler.postDelayed({
                    if(isLongPress){
                        upDatefunksion(linearLayout.id)
                    }
                }, longClickDuration.toLong())
            } else if (event.action == MotionEvent.ACTION_UP) {
                isLongPress = false
            }
            true
        })
        return isLongPress
    }

    private fun upDatefunksion(id:Int) {
        when(id){
            binding.GrammerSheet.id->{binding.GrammerSheet.visibility = View.GONE
                viewModel.getLocalData().observe(this, Observer {

                    var data  =it
                    data.grammer=true
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.SpeakingSheet.id->{binding.SpeakingSheet.visibility = View.GONE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.spiking=true
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.VocablarySheet.id->{binding.VocablarySheet.visibility = View.GONE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.vocablary=true
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.ListeningSheet.id->{binding.ListeningSheet.visibility = View.GONE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.listining=true
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.HomeWorkSheet.id->{binding.HomeWorkSheet.visibility = View.GONE
            viewModel.getLocalData().observe(this, Observer {
                var data  =it
                data.homework=true
                viewModel.updateDataDatabase(data)

            })
        }


        }
    }
    @SuppressLint("ClickableViewAccessibility")
    fun tripleTapCheck(linearLayout: LinearLayout){
        linearLayout.setOnTouchListener(
        object : OnTouchListener {
            var handler = Handler()
            var numberOfTaps = 0
            var lastTapTimeMs: Long = 0
            var touchDownMs: Long = 0
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> touchDownMs = System.currentTimeMillis()
                    MotionEvent.ACTION_UP -> {
                        handler.removeCallbacksAndMessages(null)
                        if (System.currentTimeMillis() - touchDownMs > ViewConfiguration.getTapTimeout()) {
                            //it was not a tap
                            numberOfTaps = 0
                            lastTapTimeMs = 0

                        }
                        if (numberOfTaps > 0
                            && System.currentTimeMillis() - lastTapTimeMs < ViewConfiguration.getDoubleTapTimeout()
                        ) {
                            numberOfTaps += 1
                        } else {
                            numberOfTaps = 1
                        }
                        lastTapTimeMs = System.currentTimeMillis()
                        if (numberOfTaps == 3) {

                            tripleClickAction(linearLayout.id)

                        } else if (numberOfTaps == 2) {
                            handler.postDelayed({ //handle double tap

                            }, ViewConfiguration.getDoubleTapTimeout().toLong())
                        }
                    }
                }
                return true
            }
        })

    }

    fun tripleClickAction(id: Int){
        when(id){
            binding.Grammer.id->{binding.GrammerSheet.visibility = View.VISIBLE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.grammer=false
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.Speaking.id->{binding.SpeakingSheet.visibility = View.VISIBLE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.spiking=false
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.Vocablary.id->{binding.VocablarySheet.visibility = View.VISIBLE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.vocablary=false
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.Listening.id->{binding.ListeningSheet.visibility = View.VISIBLE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.listining=false
                    viewModel.updateDataDatabase(data)

                })
            }
            binding.HomeWork.id->{binding.HomeWorkSheet.visibility = View.VISIBLE
                viewModel.getLocalData().observe(this, Observer {
                    var data  =it
                    data.homework=false
                    viewModel.updateDataDatabase(data)

                })
            }


        }
    }

}

