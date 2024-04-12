package com.example.final_project.soccer;

import java.util.List;

public class SoccerModel {
    private String title;
    private String embed;
    private String url;
    private String thumbnail;
    private String date;
    private Side side1;
    private Side side2;
    private Competition competition;
    private List<Video> videos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Side getSide1() {
        return side1;
    }

    public void setSide1(Side side1) {
        this.side1 = side1;
    }

    public Side getSide2() {
        return side2;
    }

    public void setSide2(Side side2) {
        this.side2 = side2;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
// Constructor, getters, and setters


    public static class Side {
        private String name;
        private String url;

//        public Side(String name, String url) {
//            this.name = name;
//            this.url = url;
//        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
// Constructor, getters, and setters
    }

    public class Competition {
        private String name;
        private int id;
        private String url;

        public Competition(String name, int id, String url) {
            this.name = name;
            this.id = id;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
// Constructor, getters, and setters
    }

    public static class Video {
        private String title;
        private String embed;

        public Video(String title, String embed) {
            this.title = title;
            this.embed = embed;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getEmbed() {
            return embed;
        }

        public void setEmbed(String embed) {
            this.embed = embed;
        }
// Constructor, getters, and setters
    }
}