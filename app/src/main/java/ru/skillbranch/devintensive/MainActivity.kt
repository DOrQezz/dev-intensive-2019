package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() {
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageET: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageET = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString(SAVED_STATUS) ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString(SAVED_QUESTION) ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        textTxt.text = benderObj.askQuestion()
        setBenderColor(benderObj.status.color)

        sendBtn.setOnClickListener { sendAnswer() }
        messageET.setOnEditorActionListener {v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                sendAnswer()
                true
            } else {
                false
            }
        }
    }

    private fun sendAnswer() {
        hideKeyboard()
        val answer = messageET.text.toString()
        if (answer.isEmpty()) return
        val (phrase, color) = benderObj.listenAnswer(answer)
        messageET.setText("")
        setBenderColor(color)
        textTxt.text = phrase
    }

    private fun setBenderColor(color: Triple<Int, Int, Int>) {
        val (r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.run {
            putString(SAVED_STATUS, benderObj.status.name)
            putString(SAVED_QUESTION, benderObj.question.name)
        }
    }

    companion object {
        private const val SAVED_STATUS = "STATUS"
        private const val SAVED_QUESTION = "QUESTION"

    }

}
