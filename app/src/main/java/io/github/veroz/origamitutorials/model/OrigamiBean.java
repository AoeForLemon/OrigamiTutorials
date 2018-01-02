package io.github.veroz.origamitutorials.model;

/**
 * Created by VeroZ on 2017/12/25.
 */

public class OrigamiBean {

    /**
     * id : v001
     * name : Origami 01
     * isimg : true
     * url : xx-xx-xxxx,x - street, x - country
     * pic : http://lorempixel.com/100/100/
     * description : A cat
     * details : {"size":"1MB","date":"2017-12-23"}
     */

    private String id;
    private String name;
    private boolean isimg;
    private String url;
    private String pic;
    private String description;
    private DetailsBean details;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsImg() {
        return isimg;
    }

    public void setIsImg(boolean isimg) {
        this.isimg = isimg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailsBean getDetails() {
        return details;
    }

    public void setDetails(DetailsBean details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * size : 1MB
         * date : 2017-12-23
         */

        private String size;
        private String date;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
