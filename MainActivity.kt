package br.com.aventuras.saber

import android.app.Activity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.webkit.JavascriptInterface
import android.webkit.WebView
import java.util.Locale

class MainActivity : Activity() {
    private var tts: TextToSpeech? = null

    inner class Ponte {
        @JavascriptInterface
        fun speak(texto: String) {
            tts?.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "fala")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) tts?.language = Locale("pt", "BR")
        }
        val web = WebView(this)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.addJavascriptInterface(Ponte(), "AndroidTTS")
        web.loadUrl("file:///android_asset/index.html")
        setContentView(web)
    }

    override fun onDestroy() {
        tts?.shutdown()
        super.onDestroy()
    }
}
