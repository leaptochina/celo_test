package com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models

import com.blueberrysolution.pinelib19.sqlite.mylib.user_extend_models.UserBaseModel

open class TimeStamp: UserBaseModel(){
    /**
     * 是否需要自动写入时间戳 如果设置为字符串 则表示时间字段的类型
     * @var bool|string
     */
    var autoWriteTimestamp: String? = null;

    /**
     * 创建时间字段 false表示关闭
     * @var false|string
     */
    var createTime = "create_time";

    /**
     * 更新时间字段 false表示关闭
     * @var false|string
     */
    var updateTime = "update_time";

    /**
     * 时间字段显示格式
     * @var string
     */
    var dateFormat = "H:i:s";


}