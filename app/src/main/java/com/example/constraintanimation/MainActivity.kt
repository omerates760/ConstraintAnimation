package com.example.constraintanimation

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlinx.android.synthetic.main.keyframe1.*


class MainActivity : AppCompatActivity() {
    private val constraintSet1 = ConstraintSet()
    private val constraintSet2 = ConstraintSet()

    private var isOffscreen = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.keyframe1)

        constraintSet1.clone(constraintLayout) //1
        constraintSet2.clone(this, R.layout.activity_main) //2

        departButton.setOnClickListener { //3

            //1
            val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
            val startAngle = layoutParams.circleAngle
            val endAngle = startAngle + (if (switch1.isChecked) 360 else 180)

            //2
            val anim = ValueAnimator.ofFloat(startAngle, endAngle)
            anim.addUpdateListener { valueAnimator ->

                //3
                val animatedValue = valueAnimator.animatedValue as Float
                val layoutParams = rocketIcon.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.circleAngle = animatedValue
                rocketIcon.layoutParams = layoutParams

                //4
                rocketIcon.rotation = (animatedValue % 360 - 270)
            }
            //5
            anim.duration = if (switch1.isChecked) 2000 else 1000

            //6
            anim.interpolator = LinearInterpolator()
            anim.start()
           //apply 
            TransitionManager.beginDelayedTransition(constraintLayout) //4
            val constraint = if (!isOffscreen) constraintSet1 else constraintSet2
            isOffscreen = !isOffscreen
            constraint.applyTo(constraintLayout) //5*/
        }
    }
    override fun onEnterAnimationComplete() { //1
        super.onEnterAnimationComplete()

        constraintSet2.clone(this, R.layout.activity_main) //2

        //apply the transition
        val transition = AutoTransition() //3
        transition.duration = 1000 //4
        TransitionManager.beginDelayedTransition(constraintLayout, transition) //5

        constraintSet2.applyTo(constraintLayout) //6
    }
}
