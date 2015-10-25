package org.easywechat.util;

import org.easywechat.msg.ImageRespMsg;
import org.easywechat.msg.MusicRespMsg;
import org.easywechat.msg.NewsRespMsg;
import org.easywechat.msg.TextRespMsg;
import org.easywechat.msg.VideoRespMsg;
import org.easywechat.msg.VoiceRespMsg;

public class MessageFactory {

    public static TextRespMsg createTextMsg() {
        return new TextRespMsg();
    }

    public static TextRespMsg createTextMsg(String content) {
        return new TextRespMsg(content);
    }

    public static ImageRespMsg createImageMsg(String mediaId) {
        return new ImageRespMsg(mediaId);
    }

    public static VoiceRespMsg createVoiceMsg(String mediaId) {
        return new VoiceRespMsg(mediaId);
    }

    public static VideoRespMsg createVideoMsg(String mediaId, String title,
            String description) {
        return new VideoRespMsg(mediaId, title, description);
    }

    public static MusicRespMsg createMusicMsg(String thumbMediaId, String title,
            String description, String musicUrl, String hqMusicUrl) {
        return new MusicRespMsg(thumbMediaId, title, description, musicUrl,
                hqMusicUrl);
    }

    public static NewsRespMsg createNewsMsg() {
        return new NewsRespMsg();
    }

    public static NewsRespMsg createNewsMsg(String title, String description,
            String picUrl, String url) {
        return new NewsRespMsg().add(title, description, picUrl, url);
    }

}
