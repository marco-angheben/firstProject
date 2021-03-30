package com.example.first_application

import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.first_application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Holder(binding)
    }

    inner class Holder(binding: ActivityMainBinding) {
        private var state = 0
        var gan = GAN(10)
        private val tvAttempts = binding.tvAttempts
        private val tvGAN = binding.tvGAN
        private val btnCanc = binding.btnCanc
        private val btnOK = binding.btnOK
        private val ivState = binding.ivState
        private val btns = listOf(
                binding.btn0,
                binding.btn1,
                binding.btn2,
                binding.btn3,
                binding.btn4,
                binding.btn5,
                binding.btn6,
                binding.btn7,
                binding.btn8,
                binding.btn9
        )

        init {
            for (btn in btns) {
                btn.setOnClickListener(NumberClick())
            }
            btnCanc.setOnClickListener(ActionClick())
            btnOK.setOnClickListener(ActionClick())
            ivState.setOnClickListener {
                startGame()
            }
            disableButtons()
        }

        private fun startGame() {
            gan = GAN(10)
            tvGAN.text = ""
            gan.new()
            enableButtons()
        }

        private fun isEnabled(state: Boolean) {
            for (btn in btns) {
                btn.isEnabled = state
            }
            btnCanc.isEnabled = state
            btnOK.isEnabled = state
        }


        private fun disableButtons() {
            for (btn in btns) {
                btn.isEnabled = false
            }
            btnCanc.isEnabled = false
            btnOK.isEnabled = false
        }

        private fun enableButtons() {
            for (btn in btns) {
                btn.isEnabled = true
            }
            btnCanc.isEnabled = true
            btnOK.isEnabled = true
        }

        fun check(): GAN.Answer {
            val answer = gan.check(tvGAN.text.toString().toInt())
            state = when (answer) {
                GAN.Answer.YOULOOSE -> {
                    gan.new()
                    R.drawable.you_loose
                }
                GAN.Answer.YOUWIN -> {
                    gan.new()
                    R.drawable.you_win
                }
                GAN.Answer.TOOSMALL -> R.drawable.too_small
                GAN.Answer.TOOBIG -> R.drawable.too_big
            }
            ivState.setImageResource(state)
            return answer
        }

        inner class NumberClick : View.OnClickListener {
            override fun onClick(view: View?) {
                view as Button
                val txt = tvGAN.text.toString() + view.text
                tvGAN.text = txt
            }
        }


        inner class ActionClick : View.OnClickListener {
            override fun onClick(view: View?) {
                view as Button
                if (view.id == R.id.btnCanc) {
                    tvGAN.text = ""
                }
                if (view.id == R.id.btnOK) {
                    when (check()) {
                        GAN.Answer.TOOBIG, GAN.Answer.TOOSMALL -> tvAttempts.text = gan.attempts.toString()
                        GAN.Answer.YOUWIN -> disableButtons()
                        GAN.Answer.YOULOOSE -> disableButtons()
                    }
                    tvGAN.text = ""
                    tvAttempts.text = gan.attempts.toString()
                }
            }
        }

    }
}
