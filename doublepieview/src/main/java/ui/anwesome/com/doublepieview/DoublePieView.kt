package ui.anwesome.com.doublepieview

/**
 * Created by anweshmishra on 18/03/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class DoublePieView (ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}