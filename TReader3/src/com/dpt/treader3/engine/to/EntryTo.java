package com.dpt.treader3.engine.to;


public class EntryTo {
    public  String id;
    public  String title;
    public  String link;
    public  String summary;
    public  String published;
    public  String topicIcon;
    public  String sourceName;

    public EntryTo(String id, String title, String summary, String link, String published,
            String topicIcon, String sourceName) {
        this.title = title;
        this.summary = summary;
        this.link = link;
        this.id = id;
        this.published = published;
        this.topicIcon = topicIcon;
        this.sourceName = sourceName;
    }
    
    public String getSimpleTime() {
        return published.substring(0, 10);
    }
    
    @Override
    public boolean equals(Object o) {
        EntryTo entry=(EntryTo)o;
        return this.id.equals(entry.id);
    }
    
    @Override
    public int hashCode() {
        int hashcode=-1;
        try {
            hashcode=Integer.valueOf(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return hashcode;
    }
}
