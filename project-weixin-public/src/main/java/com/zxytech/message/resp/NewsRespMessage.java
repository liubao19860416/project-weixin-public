package com.zxytech.message.resp;

import java.util.List;

/**
 * 新闻消息格式数据响应实体
 * 
 * @author Liubao
 * @2015年7月10日
 * 
 */
public class NewsRespMessage extends BaseRespMessage {
    private int ArticleCount;
    private List<Article> Articles;

    public int getArticleCount() {
        return this.ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        this.ArticleCount = articleCount;
    }

    public List<Article> getArticles() {
        return this.Articles;
    }

    public void setArticles(List<Article> articles) {
        this.Articles = articles;
    }
}