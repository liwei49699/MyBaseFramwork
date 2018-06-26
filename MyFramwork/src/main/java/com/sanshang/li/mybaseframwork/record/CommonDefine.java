package com.sanshang.li.mybaseframwork.record;

/**
 * Created by li on 2018/6/25.
 * WeChat 18571658038
 * author LiWei
 */

public class CommonDefine {

    /**
     * 多媒体例如声音的状态
     */
    public final static int STATE_UNDEFINE        = 0X0001;

    public final static int STATE_RECORD_START    = 0X0002;
    public final static int STATE_RECORD_PAUSE    = 0X0003;
    public final static int STATE_RECORD_STOP     = 0X0004;

    public final static int STATE_PLAY_START      = 0X0005;
    public final static int STATE_PLAY_PAUSE      = 0X0006;
    public final static int STATE_PLAY_STOP       = 0X0007;

    public final static int MSG_TIME_INTERVAL     = 0X0008;
}
