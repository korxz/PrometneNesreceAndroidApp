package rok.diploma;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private Button button;
    private Button buttonAccidents;
    private ImageView locationIcon;
    private Button buttonLocation;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        locationIcon = findViewById(R.id.locationIcon);
        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMapActitivty();
            }
        });


        new GetContacts().execute();
    }

    public void openMapActitivty() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }


    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openAcitivitySections();
                }
            });

            buttonAccidents = (Button) findViewById(R.id.buttonAccidents);
            buttonAccidents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openAccidentActivity();
                }
            });
        }

        public void openAcitivitySections() {
            Intent intent = new Intent(MainActivity.this, SectionActivity.class);
            startActivity(intent);
        }

        public void openAccidentActivity() {
            Intent intent = new Intent(MainActivity.this, AccidentActitivty.class);
            startActivity(intent);
        }
    }
}