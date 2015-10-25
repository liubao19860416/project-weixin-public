package org.easywechat.msg;

import java.util.ArrayList;
import java.util.List;

import org.easywechat.util.MessageBuilder;

public class NewsRespMsg extends BaseRespMsg {

    private static final int WX_MAX_SIZE = 10;

    private int maxSize = WX_MAX_SIZE;
    private List<Article> articles;

    public NewsRespMsg() {
        this.articles = new ArrayList<Article>(maxSize);
    }

    public NewsRespMsg(int maxSize) {
        setMaxSize(maxSize);
        this.articles = new ArrayList<Article>(maxSize);
    }

    public NewsRespMsg(List<Article> articles) {
        setArticles(articles);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        if (maxSize > WX_MAX_SIZE || maxSize < 1) {
            maxSize = WX_MAX_SIZE;
        }
        this.maxSize = maxSize;
        if (articles != null && articles.size() > maxSize) {
            articles = articles.subList(0, maxSize);
        }
    }

    public List<Article> getArticles() {
        return articles;
    }

    public NewsRespMsg setArticles(List<Article> articles) {
        if (articles.size() > maxSize) {
            this.articles = articles.subList(0, maxSize);
        } else {
            this.articles = articles;
        }
        return this;
    }

    public NewsRespMsg add(String title) {
        return add(title, null, null, null);
    }

    public NewsRespMsg add(String title, String url) {
        return add(title, null, null, url);
    }

    public NewsRespMsg add(String title, String picUrl, String url) {
        return add(new Article(title, null, picUrl, url));
    }

    public NewsRespMsg add(String title, String description, String picUrl,
            String url) {
        return add(new Article(title, description, picUrl, url));
    }

    public NewsRespMsg add(Article article) {
        if (this.articles.size() < maxSize) {
            this.articles.add(article);
        } else {
        }
        return this;
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespMsgType.NEWS);
        mb.addTag("ArticleCount", String.valueOf(articles.size()));
        mb.append("<Articles>\n");
        for (Article article : articles) {
            mb.append(article.toXml());
        }
        mb.append("</Articles>\n");
        mb.surroundWith("xml");
        return mb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("NewsMsg [articles=\n");
        for (int i = 0; i < articles.size(); i++) {
            sb.append("  Article ").append(i).append(": ")
                    .append(articles.get(i)).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
