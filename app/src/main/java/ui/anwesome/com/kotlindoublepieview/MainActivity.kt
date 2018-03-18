package ui.anwesome.com.kotlindoublepieview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.doublepieview.DoublePieView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DoublePieView.create(this)
    }
}
