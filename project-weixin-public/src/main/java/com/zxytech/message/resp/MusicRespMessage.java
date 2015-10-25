package com.zxytech.message.resp;
/**
 * 音乐格式数据响应实体
 * @author Liubao
 * @2015年7月10日
 *
 */
public class MusicRespMessage extends BaseRespMessage {
    private Music Music;

    public Music getMusic() {
        return this.Music;
    }

    public void setMusic(Music music) {
        this.Music = music;
    }
}