package com.blueberrysolution.pinelib19.view.edittext_send_btn

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.blueberrysolution.pinelib19.R
import com.blueberrysolution.pinelib19.addone.input_method.InputMethod

class EditTextSendBtnView : FrameLayout, TextWatcher {


    lateinit var replay_detail: EditText;
    lateinit var reply_btn: Button;
    lateinit var cancel_btn: Button;
    lateinit var replay_layout: LinearLayout;
    var onSubmitListener: ((String) -> Unit)? = null;

    constructor(context: Context) : super(context) {
        initAttrs()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs()

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (count > 0){
            cancel_btn.visibility = View.GONE;
            reply_btn.visibility = View.VISIBLE;
        }else{

            cancel_btn.visibility = View.VISIBLE;
            reply_btn.visibility = View.GONE;

        }
    }


    fun setupListener() {
        cancel_btn.setOnClickListener {
            this.reset();
        }
        replay_detail.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEND){
                var text = replay_detail.text.toString();
                submitText(text);

                true
            }
            else{
                false;
            }

        }

        reply_btn.setOnClickListener {
            var text = replay_detail.text.toString();
            submitText(text);
        }
        replay_detail.addTextChangedListener(this)

    }

    fun onSubmit(listener: (String) -> Unit){
        this.onSubmitListener = listener;

    }

    fun submitText(text: String){
        reset();

        if (onSubmitListener != null){
            onSubmitListener!!(text);
        }
    }

    fun reset(){
        this.visibility = View.GONE;
        cancel_btn.visibility = View.VISIBLE
        reply_btn.visibility = View.GONE;
        replay_detail.setText("".toCharArray(), 0, 0);

        replay_detail.setFocusable(false);
        replay_detail.setFocusableInTouchMode(false);

        InputMethod.hide(replay_detail);

    }

    fun show(defText: String = "") {
        this.visibility = View.VISIBLE;
        replay_detail.setText(defText.toCharArray(), 0, defText.length);

        InputMethod.show(replay_detail);
    }

    //进行控件和属性的关联
    private fun initAttrs() {

        //动态加载布局
        val view = LayoutInflater.from(getContext()).inflate(R.layout.edittext_btn, this)

        //最后实例化控件
        view.bringToFront()

        replay_detail = findViewById<EditText>(R.id.replay_detail);
        reply_btn = findViewById<Button>(R.id.reply_btn);
        cancel_btn = findViewById<Button>(R.id.cancel_btn);
        replay_layout = findViewById<LinearLayout>(R.id.replay_layout);

        setupListener();
    }


}