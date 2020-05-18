package rahedmir.github.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rahedmir.github.covid_19tracker.MainActivity;
import rahedmir.github.covid_19tracker.R;

public class DetailActivity extends AppCompatActivity {

    private int positionCountry;
    BottomNavigationView bottomNavigationView;
    TextView country,cases,todayCases,deaths,todaydeaths,recovered,active,critical,tests,continent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        getSupportActionBar().setTitle("Details of Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        positionCountry=intent.getIntExtra("position",0);

        country=findViewById(R.id.tvCountryx);
        cases=findViewById(R.id.tvCasesx);
        todayCases=findViewById(R.id.tvTodayCasesx);
        deaths=findViewById(R.id.tvTotalDeathsx);
        todaydeaths=findViewById(R.id.tvTodayDeathsx);
        recovered=findViewById(R.id.tvRecoveredx);
        active=findViewById(R.id.tvActivex);
        critical=findViewById(R.id.tvCriticalx);
        tests=findViewById(R.id.tvTestsx);
        continent=findViewById(R.id.tvContinentx);

        bottomNavigationView=findViewById(R.id.btnNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.world:

                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));



                        break;
                    case R.id.countries:
                        onBackPressed();


                        break;

                    case R.id.prevention:
                        Intent intent =new Intent(getApplicationContext(),prevention.class);
                        startActivity(intent);

                        break;

                }
                return true;
            }
        });

        country.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getCountry());
        cases.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getCases());
        todayCases.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getTodayCases());
        deaths.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getDeaths());
        todaydeaths.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getTodaydeaths());
        recovered.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getRecovered());
        active.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getActive());
        critical.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getCritical());
        tests.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getTests());
        continent.setText(MainActivity.AffectedCountries.countryModelList.get(positionCountry).getContinent());
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}


