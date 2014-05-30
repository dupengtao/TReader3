package com.dpt.treader3.engine.to;

import android.text.TextUtils;

public class NewsItem {

    private String title;
    private String newsUri;
    private String summary;
    private String newSIcon;
    private String time;
    private boolean isPaperItem;
    private int paperNum;
    private String newsId;

    private boolean isDownloadSelect;

    public boolean isDownloadSelect() {
        return isDownloadSelect;
    }

    public void setDownloadSelect(boolean isDownloadSelect) {
        this.isDownloadSelect = isDownloadSelect;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUri() {
        return newsUri;
    }

    public void setNewsUri(String newsUri) {
        this.newsUri = newsUri;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNewSIcon() {
        return newSIcon;
    }

    public void setNewSIcon(String newSIcon) {
        this.newSIcon = newSIcon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isPaperItem() {
        return isPaperItem;
    }

    public void setPaperItem(boolean isPaperItem) {
        this.isPaperItem = isPaperItem;
    }

    public int getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(int paperNum) {
        this.paperNum = paperNum;
    }

    public String getId() {

        String id = this.getNewsId();

        if (TextUtils.isEmpty(id)) {
            return "-1";
        }
        String[] arr = id.split("_");
        return arr[arr.length - 1];

// String uri = getNewsUri();
// // if (!TextUtils.isEmpty(uri)) {
// // String[] split = uri.split("/");
// // return split[split.length - 1].substring(0, 7);
// // }
// if (TextUtils.isEmpty(uri)) {
// return null;
// }
// if (uri.endsWith(".html")) {
// uri=uri.replace(".html", "");
// String[] split = uri.split("/");
// return split[split.length - 1];
// } else {
// return uri.substring(uri.lastIndexOf("n/") + 2,
// uri.length() - 1);
// }

    }

    @Override
    public boolean equals(Object o) {

        try {
            NewsItem itme = (NewsItem) o;
            if (this.isPaperItem || itme.isPaperItem) {
                return this.paperNum == itme.paperNum;
            }
            if (this != null && this.getId() != null && itme != null && itme.getId() != null) {
                return this.getId().equals(itme.getId());
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int hashCode() {

        try {
            if (isPaperItem) {
                return this.paperNum;
            } else {
                return Integer.parseInt(this.getId());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return (int) System.currentTimeMillis();
        }
    }

}
