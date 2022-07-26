package com.example.ironsourcetest.presentation

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ironsourcetest.R
import com.example.ironsourcetest.core.Result
import com.example.ironsourcetest.Utils
import com.example.ironsourcetest.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: ActionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vm.action.observe(this) { action ->
            checkAction(action)
        }

        binding.btnDoAction.setOnClickListener { vm.doAction() }
    }

    private fun checkAction(action: Result<ActionUi>) {
        when (action) {
            is Result.Success -> {
                when(action.result.action) {
                    Action.NOTIFICATION -> {
                        NotificationHelper.showNotification(this)
                    }
                    Action.ANIMATION -> {
                        val animation = AnimationUtils.loadAnimation(this, R.anim.animation)
                        binding.btnDoAction.startAnimation(animation)
                    }
                    Action.TOAST -> {
                        Toast.makeText(this, "Action is Toast!", Toast.LENGTH_SHORT).show()
                    }
                    Action.CALL -> {
                        startActivity(Utils.getContactIntent())
                    }
                }
            }
            is Result.Failure -> {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}