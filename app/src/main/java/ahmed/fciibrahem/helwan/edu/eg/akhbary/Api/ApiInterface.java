package ahmed.fciibrahem.helwan.edu.eg.akhbary.Api;

import ahmed.fciibrahem.helwan.edu.eg.akhbary.Model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getnews(
            @Query("country") String country,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<News> getSearchNews(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey



    );
}
