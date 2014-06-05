package com.dpt.treader3.net;




public class UriHelper {
	
    private static final String BASE_URL=" http://wcf.open.cnblogs.com/news/";
    
    /**
     * 分页获取最新新闻列表
     * http://wcf.open.cnblogs.com/news/recent/paged/{PAGEINDEX}/{PAGESIZE}
     * @return
     */
    public static String getRecentNews(int pageIndex,int pageSize) {
        StringBuilder builder = new StringBuilder(BASE_URL);
        builder.append("recent/paged/");
        builder.append(pageIndex);
        builder.append("/");
        builder.append(pageSize);
        return builder.toString();
    }
    
    
	public static String getBlogItemBody(String id){
		StringBuilder builder = new StringBuilder(Constants.BASE_BLOG_URI);
		builder.append("post/body/");
		builder.append(id);		
		return builder.toString();
	}
	public static String getNewsItemBody(String id){
		StringBuilder builder = new StringBuilder(Constants.BASE_NEWS_URI);
		builder.append("item/");
		builder.append(id);		
		return builder.toString();
	}
	
}
