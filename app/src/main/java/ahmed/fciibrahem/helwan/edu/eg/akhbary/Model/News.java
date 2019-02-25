package ahmed.fciibrahem.helwan.edu.eg.akhbary.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class News {
    @SerializedName("status")
    @Expose
    private String Status;

    @SerializedName("totalResults")
    @Expose
    private int TotalResalt;

    @SerializedName("articles")
    @Expose
    private List<Article> Articles;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getTotalResalt() {
        return TotalResalt;
    }

    public void setTotalResalt(int totalResalt) {
        TotalResalt = totalResalt;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> articles) {
        Articles = articles;
    }
}
