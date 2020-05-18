package rahedmir.github.covid_19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvCases, tvRecorded, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    BottomNavigationView bottomNavigationView;
    RelativeLayout relativeLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        tvCases=findViewById(R.id.tvCases);
        tvRecorded=findViewById(R.id.tvRecovered);
        tvCritical=findViewById(R.id.tvCritical);
        tvActive=findViewById(R.id.tvActive);
        tvTodayCases=findViewById(R.id.tvTodayCases);
        tvTotalDeaths=findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths=findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries=findViewById(R.id.tvAffectedCountries);

        simpleArcLoader=findViewById(R.id.loader);
        scrollView=findViewById(R.id.scrollStats);
        pieChart=findViewById(R.id.piechart);

        bottomNavigationView=findViewById(R.id.btnNav);

        relativeLayout=findViewById(R.id.noConnection);


        fetchData();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.world:



                        break;
                    case R.id.countries:
                        startActivity(new Intent(getApplicationContext(), AffectedCountries.class));
                        AffectedCountries.countryModelList.clear();
                        break;

                    case R.id.prevention:
                        Intent intent =new Intent(getApplicationContext(),prevention.class);
                        startActivity(intent);
                        break;

                }
                return true;
            }
        });



    }

    private void fetchData() {
        String url="https://corona.lmao.ninja/v2/all/";
        simpleArcLoader.start();
        StringRequest request= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response.toString());
                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecorded.setText(jsonObject.getString("recovered"));

                            tvCritical.setText(jsonObject.getString("critical"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));
                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecorded.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5305")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Intent intent =new Intent(getApplicationContext(),noConnection.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);






    }


    @Override
    public void onBackPressed() {
        finishAffinity();
        super.onBackPressed();
    }
    public static class AffectedCountries extends AppCompatActivity {
    EditText edtSearch;
    ListView listView;

    BottomNavigationView bottomNavigationView;

TextView textView;

    public static List<CountryModel> countryModelList=new ArrayList<>();
    CountryModel countryModel;
    rahedmir.github.covid_19tracker.myCustomAdapter myCustomAdapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_affected_countries);
            edtSearch=findViewById(R.id.edtSearch);
            listView=findViewById(R.id.listView);

            textView=findViewById(R.id.txtCountry);

            getSupportActionBar().setTitle("Affected Countries");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            bottomNavigationView=findViewById(R.id.btnNav);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    startActivity(new Intent(getApplicationContext(), DetailActivity.class).putExtra("position",position));
                }
            });


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

                            break;

                        case R.id.prevention:
                            Intent intent =new Intent(getApplicationContext(),prevention.class);
                            startActivity(intent);

                            break;

                    }
                    return true;
                }
            });



          edtSearch.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence s, int start, int count, int after) {

              }

              @Override
              public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();
              }

              @Override
              public void afterTextChanged(Editable s) {

              }
          });
            fetchData();
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId()==android.R.id.home)
            {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

        private void fetchData() {
            String url="https://corona.lmao.ninja/v2/countries/";

            StringRequest request= new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    String countryName=jsonObject.getString("country");
                                    String cases=jsonObject.getString("cases");
                                    String todaycases=jsonObject.getString("todayCases");
                                    String deaths=jsonObject.getString("deaths");
                                    String todaydeaths=jsonObject.getString("todayDeaths");
                                    String recorved=jsonObject.getString("recovered");
                                    String active=jsonObject.getString("active");
                                    String critical=jsonObject.getString("critical");
                                    String tests=jsonObject.getString("tests");
                                    String continent=jsonObject.getString("continent");

                                    JSONObject object=jsonObject.getJSONObject("countryInfo");
                                    String flagUrl=object.getString("flag");

                                    countryModel=new CountryModel(flagUrl,countryName,cases,todaycases,deaths,todaydeaths,recorved,active,critical,tests,continent);
                                    countryModelList.add(countryModel);

                                    myCustomAdapter=new myCustomAdapter(AffectedCountries.this,countryModelList);

                                    listView.setAdapter(myCustomAdapter);



                                }




                            } catch (JSONException e) {
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Intent intent =new Intent(getApplicationContext(),noConnection.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });

            RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);
        }
    }
}
