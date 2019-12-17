package com.blueberrysolution.pinelib19.pop_up_windows.msgbox

class MessageBoxListener(val func: (Int) -> Unit) : OnMessageClickListener{

    override fun messageBoxChoose(id: Int) {
        func(id);
    }

}