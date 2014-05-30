package com.dpt.treader3.engine.to;

public class ArticleTo {

    private String SubmitDate;
    private String SourceName;
    private String PrevNews;
    private String NextNews;
    private String ImageUrl;
    private String Content;
    private String Title;

    public ArticleTo(String submitDate, String sourceName, String prevNews,
            String nextNews, String imageUrl, String content, String title) {
        super();
        SubmitDate = submitDate;
        SourceName = sourceName;
        PrevNews = prevNews;
        NextNews = nextNews;
        ImageUrl = imageUrl;
        Content = content;
        Title = title;
    }

    public String getSubmitDate() {
        return SubmitDate;
    }

    public void setSubmitDate(String submitDate) {
        SubmitDate = submitDate;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public String getPrevNews() {
        return PrevNews;
    }

    public void setPrevNews(String prevNews) {
        PrevNews = prevNews;
    }

    public String getNextNews() {
        return NextNews;
    }

    public void setNextNews(String nextNews) {
        NextNews = nextNews;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
