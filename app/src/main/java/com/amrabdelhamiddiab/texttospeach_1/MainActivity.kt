package com.amrabdelhamiddiab.texttospeach_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.amrabdelhamiddiab.texttospeach_1.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private lateinit var btnSpeak: Button
    private lateinit var etSpeak: EditText
    private lateinit var binding: ActivityMainBinding
    val local = Locale("ar")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSpeak.isEnabled = false

        val voiceName = local.toLanguageTag()
        val voice = Voice(voiceName, local, Voice.QUALITY_HIGH, Voice.LATENCY_HIGH, false, null)


        // TextToSpeech(Context: this, OnInitListener: this)
        tts = TextToSpeech(this, this)
        tts!!.voice = voice
        binding.btnSpeak.setOnClickListener {
            speakOut()
        }


    }

    private fun speakOut() {
        val text = binding.etInput.text.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(p0: Int) {
        if (p0 == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(local)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language not supported!")
            } else {
                binding.btnSpeak.isEnabled = true
            }
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}