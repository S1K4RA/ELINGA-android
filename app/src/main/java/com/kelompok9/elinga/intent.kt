package com.kelompok9.elinga

import android.content.Intent

class intent {
    val url = "https://meet.google.com/qut-twpj-jpx"

    val eIntent = Intent(Intent.ACTION_VIEW)
    eIntent.setData(Uri.parse(url))
    startActivity(eIntent)
}