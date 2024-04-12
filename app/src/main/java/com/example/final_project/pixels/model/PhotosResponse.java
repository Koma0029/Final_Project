package com.example.final_project.pixels.model;

import java.util.List;

public class PhotosResponse {
    private String next_page = "";
    private int page = 0;
    private int per_page = 0;
    private List<Photo> photos = null;
    private int total_results = 0;

    public String getNext_page() {
        return next_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public static class Photo {
        private String alt = "";
        private String avg_color = "";
        private int height = 0;
        private int id = 0;
        private boolean liked = false;
        private String photographer = "";
        private int photographer_id = 0;
        private String photographer_url = "";

        private  Src src ;
        private String url = "";


        public Photo(  int id, String photographer, int photographer_id,String photographer_url,  int height, int width, String url, Src src) {
            this.alt = alt;
            this.avg_color = avg_color;
            this.height = height;
            this.id = id;
            this.liked = liked;
            this.photographer = photographer;
            this.photographer_id = photographer_id;
            this.photographer_url = photographer_url;
            this.src = src;
            this.url = url;
            this.width = width;
        }

        public Src getSrc() {
            return src;
        }

        public void setSrc(Src src) {
            this.src = src;
        }

        private int width = 0;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getAvg_color() {
            return avg_color;
        }

        public void setAvg_color(String avg_color) {
            this.avg_color = avg_color;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public String getPhotographer() {
            return photographer;
        }

        public void setPhotographer(String photographer) {
            this.photographer = photographer;
        }

        public int getPhotographer_id() {
            return photographer_id;
        }

        public void setPhotographer_id(int photographer_id) {
            this.photographer_id = photographer_id;
        }

        public String getPhotographer_url() {
            return photographer_url;
        }

        public void setPhotographer_url(String photographer_url) {
            this.photographer_url = photographer_url;
        }



        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public static class Src {

            public Src(String original)
            {
                this.original= original;
            }
            private String landscape = "";
            private String large = "";
            private String large2x = "";
            private String medium = "";
            private String original = "";
            private String portrait = "";
            private String small = "";
            private String tiny = "";

            public String getLandscape() {
                return landscape;
            }

            public void setLandscape(String landscape) {
                this.landscape = landscape;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getLarge2x() {
                return large2x;
            }

            public void setLarge2x(String large2x) {
                this.large2x = large2x;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getTiny() {
                return tiny;
            }

            public void setTiny(String tiny) {
                this.tiny = tiny;
            }
        }
    }
}
