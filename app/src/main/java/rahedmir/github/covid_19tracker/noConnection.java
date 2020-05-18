package rahedmir.github.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class noConnection extends AppCompatActivity {
  Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);
        button=findViewById(R.id.btnNoConnection);

      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              checkConnection();
          }
      });

    }

    private void checkConnection()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isConnected())
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        else if (mobile.isConnected())
        {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        else
        {
            Toast toast= Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT);
            View view=toast.getView();
            view.setBackgroundColor(Color.GRAY);
            TextView text =(TextView)view.findViewById(android.R.id.message);
            text.setShadowLayer(0,0,0,Color.TRANSPARENT);
            text.setTextColor(Color.WHITE);

            toast.show();
        }
    }

}
