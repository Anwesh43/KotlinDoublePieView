package ui.anwesome.com.kotlindoublepieview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.doublepieview.DoublePieView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = DoublePieView.create(this)
        view.addDoublePieListener({
            Toast.makeText(this, "closed", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "opened", Toast.LENGTH_SHORT).show()
        })
        fullScreen()
    }
}
fun MainActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
