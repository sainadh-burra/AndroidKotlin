package com.sainadh.basiclayoutdemo

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sainadh.basiclayoutdemo.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var drawableResource: MutableLiveData<Int>
    var diceValue: Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        val myFirstDice = Dice(6)
        drawableResource= MutableLiveData( 0)
        drawableResource.observe(this, Observer {
            if(it!=0) {
                binding.mainDiceIv.setImageResource(it)
                binding.mainDicevalueTv.text = diceValue.toString()
                val aniFadein =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
                binding.mainRollBt.startAnimation(aniFadein)
                binding.mainRollBt.isEnabled = true
//            binding.mainRollBt.visibility= View.VISIBLE
                val aniZoomin =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
                binding.mainDiceStatusTv.startAnimation(aniZoomin)
                binding.mainDiceStatusTv.text = "Status : Rolled"
                binding.mainRollBt.text="Roll"
            }
        })

        binding.mainRollBt.setOnClickListener {

            binding.mainRollBt.isEnabled=false
            val aniFadeout =
                AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
            binding.mainRollBt.startAnimation(aniFadeout)
//            binding.mainRollBt.visibility= View.GONE
            binding.mainRollBt.text="Rolling"
            binding.mainDiceStatusTv.text="Status : Rolling"

            val aniRotate =
                AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)

            binding.mainDiceIv.startAnimation(aniRotate)



                Timer("AfterAnimation", false).schedule(1000) {
                    diceValue=myFirstDice.roll()
                    drawableResource.postValue(when (diceValue) {
                        1 -> R.drawable.dice_1
                        2 -> R.drawable.dice_2
                        3 -> R.drawable.dice_3
                        4 -> R.drawable.dice_4
                        5 -> R.drawable.dice_5
                        else -> R.drawable.dice_6
                    })

                    //error java.lang.IllegalStateException: Cannot invoke setValue on a background thread
                    /*drawableResource.value=when (diceValue) {
                        1 -> R.drawable.dice_1
                        2 -> R.drawable.dice_2
                        3 -> R.drawable.dice_3
                        4 -> R.drawable.dice_4
                        5 -> R.drawable.dice_5
                        else -> R.drawable.dice_6
                    }*/

                }

//            val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
//
//            valueAnimator.addUpdateListener {
//                val value = it.animatedValue as Float
//                binding.mainDiceIv.rotation = value
//            }
//
//            valueAnimator.interpolator = CycleInterpolator(360f)
//            valueAnimator.duration = 5000
//            valueAnimator.addListener(object : Animator.AnimatorListener {
//                override fun onAnimationStart(animation: Animator) {
//
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
//                    binding.mainDiceIv.setImageResource(drawableResource)
//                    binding.mainDicevalueTv.text=diceValue.toString()
//                }
//
//                override fun onAnimationCancel(animation: Animator) {}
//
//                override fun onAnimationRepeat(animation: Animator) {}
//            })
//            valueAnimator.start()

//            Toast.makeText(this, "Dice Rolled!", Toast.LENGTH_SHORT).show()
        }

    }
}