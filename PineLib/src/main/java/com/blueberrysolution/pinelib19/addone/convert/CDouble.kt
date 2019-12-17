package com.blueberrysolution.pinelib19.addone.convert

import java.text.DecimalFormat


fun Double.ToString(format: String): String{ //#.##
    val df = DecimalFormat(format)
    return df.format(this);
}