package sg.belive.testlottie

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val minAni: Int = 88
    val maxAni: Int = 95
    var aniIndex: Int = minAni


    private lateinit var ramUsageRunable: Runnable
    private var handler: Handler = Handler()

    init {
        ramUsageRunable = Runnable {
            handler.removeCallbacks(ramUsageRunable)

            val runtime = Runtime.getRuntime()
            val usedMemInMB = (runtime.totalMemory() - runtime.freeMemory()).toFloat() / 1048576f
            val maxHeapSizeInMB = runtime.maxMemory().toFloat() / 1048576f
            val availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB
            val usedMemInPercentage2 = usedMemInMB * 100f / maxHeapSizeInMB

            tvRamStats.text = "total mem: $maxHeapSizeInMB\nfree mem: $availHeapSizeInMB\nused mem: $usedMemInMB\n% usage: $usedMemInPercentage2"

            handler.postDelayed(ramUsageRunable, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lav_thumbUp.addAnimatorListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                aniIndex++
                if (aniIndex > maxAni)
                {
                    aniIndex = minAni
                }else {
                    doAnimation()
                }

                Log.e("ANIMATION", "$aniIndex ANI")
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })

        doAnimation()
        handler.post(ramUsageRunable)
    }

    fun doAnimation()
    {
        Log.e("API", "ani$aniIndex.json")

        lav_thumbUp.setAnimation("ani$aniIndex.json")
        lav_thumbUp.progress = 0F
        lav_thumbUp.pauseAnimation()
        lav_thumbUp.playAnimation()
    }
}
