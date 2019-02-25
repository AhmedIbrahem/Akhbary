package ahmed.fciibrahem.helwan.edu.eg.akhbary.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {
    @SerializedName("source")
    @Expose
    private Source source;

    @SerializedName("author")
    @Expose
    private String Author;


    @SerializedName("title")
    @Expose
    private String Title;

    @SerializedName("description")
    @Expose
    private String Description;

    @SerializedName("url")
    @Expose
    private String Url;

    @SerializedName("urlToImage")
    @Expose
    private String UrlToImage;

    @SerializedName("publishedAt")
    @Expose
    private String PublishedAt;

    @SerializedName("content")
    @Expose
    private String Content;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUrlToImage() {
        return UrlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        UrlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return PublishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        PublishedAt = publishedAt;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
