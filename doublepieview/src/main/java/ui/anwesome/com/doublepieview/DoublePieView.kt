package ui.anwesome.com.doublepieview

/**
 * Created by anweshmishra on 18/03/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class DoublePieView (ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Int = 0, var j : Int = 0) {
        val scales : Array<Float> = arrayOf(0f, 0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir
                if (j == scales.size || j == -1) {
                    j -= dir
                    dir = 0
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0) {
                dir = 1 - 2 * prevScale.toInt()
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch (ex : Exception) {

                }
            }
        }
        fun start () {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop () {
            if (animated) {
                animated = false
            }
        }
    }
    data class DoublePie(var i : Int, var state : State = State()) {
        fun draw(canvas : Canvas, paint : Paint) {
            val w = canvas.width.toFloat()
            val h = canvas.height.toFloat()
            paint.strokeWidth = Math.min(w, h)/ 50
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(w/2, h/2)
            for(i in 0..1) {
                canvas.save()
                canvas.translate((w/4) * state.scales[1] * (1 - 2 * i), 0f)
                paint.style = Paint.Style.STROKE
                canvas.drawArc(RectF(-w/4, -w/4, w/4, w/4), 0f, 360f * state.scales[0], false, paint)
                paint.style = Paint.Style.FILL
                canvas.drawArc(RectF(-w/4, -w/4, w/4, w/4), 0f , 360f * state.scales[2], true, paint)
                canvas.restore()
            }
            canvas.restore()
        }
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class Renderer(var view : DoublePieView) {
        val doublePie : DoublePie = DoublePie(0)
        val animator = Animator(view)
        fun render (canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            paint.color = Color.parseColor("#e74c3c")
            doublePie.draw(canvas, paint)
            animator.animate {
                doublePie.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            doublePie.startUpdating {
                animator.start()
            }
        }
    }
}