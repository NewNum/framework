

package com.app.framework.common.callback


import android.text.Editable

import android.text.TextWatcher


/**
 * @author zongjin
 * @date 2019/3/12.
 */
open class TextWatcherListener : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}