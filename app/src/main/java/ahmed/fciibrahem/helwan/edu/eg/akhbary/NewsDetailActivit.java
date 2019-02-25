package ahmed.fciibrahem.helwan.edu.eg.akhbary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class NewsDetailActivit extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private ImageView imageView;
    private TextView appbar_title, appbar_subtitle, date, time, title;
    private boolean IsHideToolbarView = false;
    private FrameLayout datebehvier;
    private LinearLayout appbartitle;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private String ImgURL, MImg, Mtitle, Mdate, Msource, Mauthor;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);

        datebehvier = findViewById(R.id.date_behavior);
        appbar_title = findViewById(R.id.title_on_appbar);
        appbar_subtitle = findViewById(R.id.subtitle_on_appbar);
        imageView=findViewById(R.id.backdrop);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        title = findViewById(R.id.title);
        Intent intent=getIntent();
        ImgURL=intent.getStringExtra("url");
        MImg=intent.getStringExtra("img");
        Mtitle=intent.getStringExtra("title");
        Mdate=intent.getStringExtra("date");
        Msource=intent.getStringExtra("source");
        Mauthor=intent.getStringExtra("author");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(Utils.getRandomDrawbleColor());
        Glide.with(this).load(MImg).apply(requestOptions).transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
        appbar_title.setText(Msource);
        appbar_subtitle.setText(ImgURL);
        date.setText(Utils.DateFormat(Mdate));
        title.setText(Mtitle);

        String author = null;
        if(Mauthor!=null ||Mauthor!=""){
            Mauthor="\u2022"+Mauthor;
        }else {
            author="";
        }
        time.setText(Msource+author+"\u2022"+Utils.DateToTimeFormat(Mdate));
        initwebview(ImgURL);






    }

    private void initwebview(String url){
        WebView webView=findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        int maxscroll = appBarLayout.getTotalScrollRange();
        float precentage = (float) Math.abs(i) / (float) maxscroll;
        if (precentage == 1f && IsHideToolbarView) {
            datebehvier.setVisibility(View.GONE);
            appbar_title.setVisibility(View.VISIBLE);
            IsHideToolbarView = !IsHideToolbarView;
        } else if (precentage < 1f && IsHideToolbarView) {
            datebehvier.setVisibility(View.VISIBLE);
            appbar_title.setVisibility(View.GONE);
            IsHideToolbarView = !IsHideToolbarView;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_openbrowser){
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(ImgURL));
            startActivity(intent);
            return true;
        }else if(id==R.id.action_share) {
            try{
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plan");
                intent.putExtra(Intent.EXTRA_SUBJECT,Msource);
                String body=Mtitle+"\n"+ImgURL+"\n"+"Share from News Apps"+"\n";
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent,"Sharewhite :"));

            }catch (Exception e){
                Toast.makeText(this, "Cantbe share", Toast.LENGTH_SHORT).show();

            }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.news_menu, menu);
        return true;
    }
}
