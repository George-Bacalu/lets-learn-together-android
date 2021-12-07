package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

public class VideoPlayerActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";

    private CardView categoryCard;
    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID, -1);
            if(categoryId != -1) {
                Category incomingCategory = Utils.getInstance(this).getCategoryById(categoryId);
                if(incomingCategory != null) {
                    setData(incomingCategory);
                    handleFavorite(incomingCategory);
                }
            }
        }

        String videoSubject = intent.getStringExtra(CATEGORY_NAME).toLowerCase();
        String videoSubjectFormatted = replaceDiacriticsAndSpaces(videoSubject);

        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoSubjectFormatted;
        videoView.setVideoPath(uriPath);
        // String dynamicImageResource = "drawable/" + videoSubjectFormatted;
        // int imageKey = getResources().getIdentifier(dynamicImageResource, "drawable", getPackageName());

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        categoryCard = findViewById(R.id.categoryCard);
        favoriteButton = findViewById(R.id.favoriteButton);
        videoView = findViewById(R.id.videoView);
    }

    private void setData(Category category) {
        categoryName.setText(category.getName());
        Glide.with(this).asBitmap().load(category.getImageSource()).into(categoryImage);
    }

    private void handleFavorite(Category incomingCategory) {
        ArrayList<Category> favoriteCategories = Utils.getInstance(this).getFavoriteCategories();

        boolean existInFavoriteCategories = false;
        for(Category category : favoriteCategories) {
            if(category.getId() == incomingCategory.getId()) {
                existInFavoriteCategories = true;
            }
        }
        if(existInFavoriteCategories) {
            favoriteButton.setText("Elimină favorit");
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerActivity.this);
                    builder.setMessage("Eşti sigur că vrei să elimini " + incomingCategory.getName() + " de la favorite?");
                    builder.setPositiveButton("DA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String chosenToBeRemoved = incomingCategory.getName();
                            if(Utils.getInstance(VideoPlayerActivity.this).removedFromFavorite(incomingCategory)) {
                                Toast.makeText(VideoPlayerActivity.this,  chosenToBeRemoved + " eliminat", Toast.LENGTH_SHORT).show();
                                favoriteButton.setText("Adaugă la favorite");
                                Intent intent = new Intent(VideoPlayerActivity.this, VideoSectionActivity.class);
                                VideoPlayerActivity.this.startActivity(intent);
                                handleFavorite(incomingCategory);
                            } else {
                                Toast.makeText(VideoPlayerActivity.this, "Ceva nu e bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("NU", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });
                    builder.create().show();
                }
            });
        } else {
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(VideoPlayerActivity.this).addedToFavorite(incomingCategory)) {
                        Toast.makeText(VideoPlayerActivity.this, incomingCategory.getName() + " adăugat la favorite", Toast.LENGTH_SHORT).show();
                        handleFavorite(incomingCategory);
                    } else {
                        Toast.makeText(VideoPlayerActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private static String replaceDiacriticsAndSpaces(String str) {
        char[] charArray = str.toCharArray();
        for(int i = 0; i < charArray.length; i++) {
            switch(charArray[i]) {
                case ' ': charArray[i] = '_'; break;
                case 'ă':
                case 'â': charArray[i] = 'a'; break;
                case 'î': charArray[i] = 'i'; break;
                case 'ş': charArray[i] = 's'; break;
                case 'ţ': charArray[i] = 't'; break;
                default: break;
            }
        }
        return String.valueOf(charArray);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}