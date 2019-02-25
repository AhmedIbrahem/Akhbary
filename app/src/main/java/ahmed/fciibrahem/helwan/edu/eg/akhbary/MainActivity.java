package ahmed.fciibrahem.helwan.edu.eg.akhbary;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.akhbary.Api.ApiClient;
import ahmed.fciibrahem.helwan.edu.eg.akhbary.Api.ApiInterface;
import ahmed.fciibrahem.helwan.edu.eg.akhbary.Model.Article;
import ahmed.fciibrahem.helwan.edu.eg.akhbary.Model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    public static final String API_KEY = "9954ba7a585a48d4a775e283ccd43674";
    private RecyclerView newsrecyclar;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList();
    private Adpter adpter;
    private String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout errorlayout;
    private ImageView errorimage;
    private TextView errortitle,errorbody;
    private Button btn_retery;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorlayout=findViewById(R.id.error_layout);
        errorimage=findViewById(R.id.error_image);
        errortitle=findViewById(R.id.error_title);
        errorbody=findViewById(R.id.error_message);
        btn_retery=findViewById(R.id.btn_retery);
        newsrecyclar = findViewById(R.id.News_ecyclar_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        newsrecyclar.setLayoutManager(layoutManager);
        newsrecyclar.setItemAnimator(new DefaultItemAnimator());
        newsrecyclar.setNestedScrollingEnabled(false);
        swipeRefreshLayout=findViewById(R.id.swipe_refrich);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);

        onloadingswiperefrish("");


    }

    public void LoadData(final String keycord) {
        errorlayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        String country = Utils.getCountry();
        Log.d("connn", "LoadData: " + country);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call;
        if(keycord.length()>0){
            call=apiInterface.getSearchNews(keycord,"ar","publishedAt",API_KEY);

        }else {
            call = apiInterface.getnews(country, API_KEY);
        }
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    if (!articles.isEmpty()) {
                        articles.clear();
                    }
                    articles = response.body().getArticles();
                    adpter = new Adpter(articles, MainActivity.this);
                    newsrecyclar.setAdapter(adpter);
                    adpter.notifyDataSetChanged();
                    initListener();
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    String error_code;
                    switch (response.code()){
                        case 404:
                            error_code="404 Not found";
                            break;
                        case 500:
                            error_code="500 Server broken";
                            break;
                            default:
                                error_code="Unkhnown error";

                    }
                    showErrorMessage(R.drawable.no_result,"No Resualt","Please Try Again !"+error_code);

                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showErrorMessage(R.drawable.no_result,"Opps ...","Network Falier ,Please Try Again !"+t.toString());



            }
        });

    }
    private void initListener(){
        adpter.SetOnClickItemListner(new Adpter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int posation) {
                ImageView imageView=view.findViewById(R.id.img);

                Intent intent=new Intent(MainActivity.this,NewsDetailActivit.class);
                Article article=articles.get(posation);
                intent.putExtra("url",article.getUrl());
                intent.putExtra("title",article.getTitle());
                intent.putExtra("img",article.getUrlToImage());
                intent.putExtra("date",article.getPublishedAt());
                intent.putExtra("source",article.getSource().getName());
                intent.putExtra("author",article.getAuthor());
                Pair<View,String> pair= Pair.create((View)imageView, ViewCompat.getTransitionName(imageView));
                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,pair);
                startActivity(intent,activityOptionsCompat.toBundle());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("ادخل كلمه البحث");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2) {
                    onloadingswiperefrish(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                onloadingswiperefrish(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;

    }

    @Override
    public void onRefresh() {
        LoadData("");
    }
    private void onloadingswiperefrish(final String keyword){
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                LoadData(keyword);
            }
        });
    }


    private void showErrorMessage(int imageview,String title,String message)
    {
        if(errorlayout.getVisibility()==View.GONE){
            errorlayout.setVisibility(View.VISIBLE);

        }
        errortitle.setText(title);
        errorbody.setText(message);
        errorimage.setImageResource(imageview);
        btn_retery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onloadingswiperefrish("");
            }
        });


    }
}


