package com.example.llt_project_separate;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.llt_project_separate.expressions_section.ExpressionsActivity;
import com.example.llt_project_separate.general.standard_classes.Section;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.example.llt_project_separate.text_to_sign_section.TextToSignSectionActivity;
import com.example.llt_project_separate.user.UserActivity;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.example.llt_project_separate.voice_to_sign_section.VoiceToSignSectionActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private RecyclerView sectionsRecyclerView;
   private MainRecyclerViewAdapter sectionsAdapter;

   private DrawerLayout drawerLayout;
   private NavigationView navigationView;
   private ActionBarDrawerToggle toggle;
   private Toolbar toolbar;
   private MaterialButton userNavigationButton;

   //private MenuItem navHome, navVideo, navTextToSign, navVoiceToSign, navExpressions, navSettings;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      initializeViews();

      sectionsRecyclerView.setAdapter(sectionsAdapter);
      sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      UtilsRetrofit.getInstance(this).getAllSections(new Callback<List<Section>>() {
         @Override
         public void onResponse(@NonNull Call<List<Section>> call, @NonNull Response<List<Section>> response) {
            if (response.isSuccessful()) {
               List<Section> sections = response.body();
               for (Section section : sections) {
                  String name = section.getName();
                  //section.setId(getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName()));
                  section.setIcon(getResources().getIdentifier("ic_" + name.replace(" ", "_").toLowerCase(), "drawable", getPackageName()));
                  section.setImageSource(getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName()));
               }
               sectionsAdapter.setSections(sections);
            } else {
               Toast.makeText(MainActivity.this, "Obtinerea sectiunilor a esuat!", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(@NonNull Call<List<Section>> call, @NonNull Throwable throwable) {
            Toast.makeText(MainActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
         }
      });

      userNavigationButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, UserActivity.class)));

      toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      drawerLayout = findViewById(R.id.drawerLayout);
      //NavigationUI.setupWithNavController();


      toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();

      navigationView = findViewById(R.id.navigationView);
      navigationView.setNavigationItemSelectedListener(this);
   }

   private void initializeViews() {
      sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
      sectionsAdapter = new MainRecyclerViewAdapter(this);
      userNavigationButton = findViewById(R.id.button_userNavigation);
        /*
        navHome = findViewById(R.id.navHome);
        navVideo = findViewById(R.id.navVideo);
        navTextToSign = findViewById(R.id.navTextToSign);
        navVoiceToSign = findViewById(R.id.navVoiceToSign);
        navExpressions = findViewById(R.id.navExpressions);
        navSettings = findViewById(R.id.navSettings)*/
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater menuInflater = getMenuInflater();
      menuInflater.inflate(R.menu.action_bar_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.navSettingsActionBar) {
         Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
         startActivity(intent);
      }
      return super.onOptionsItemSelected(item);
   }

   @SuppressLint("NonConstantResourceId")
   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      Intent intent;
      switch (item.getItemId()) {
         case R.id.navHome: intent = new Intent(MainActivity.this, MainActivity.class);break;
         case R.id.navVideo: intent = new Intent(MainActivity.this, VideoSectionActivity.class);break;
         case R.id.navTextToSign: intent = new Intent(MainActivity.this, TextToSignSectionActivity.class);break;
         case R.id.navVoiceToSign: intent = new Intent(MainActivity.this, VoiceToSignSectionActivity.class);break;
         case R.id.navExpressions: intent = new Intent(MainActivity.this, ExpressionsActivity.class);break;
         case R.id.navSettings: intent = new Intent(MainActivity.this, SettingsActivity.class);break;
         default: throw new IllegalStateException("Unexpected value: " + item.getItemId());
      }
      startActivity(intent);
      return true;
   }

   String getStringResource(int intResource) {
      return getResources().getString(intResource);
   }
}