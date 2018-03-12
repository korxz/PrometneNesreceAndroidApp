package rok.diploma;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AccidentActitivty extends AppCompatActivity {

    private String TAG = AccidentActitivty.class.getSimpleName();
    private GridView lv;
    private Button button;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident);

        contactList = new ArrayList<>();
        lv = (GridView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    public String translateKlasNescerec(String klas) {
        String prevod = "";
        switch (klas) {
            case "B":
                prevod = "Brez poskodb";
                break;
            case "L":
                prevod = "Lazja poskodba";
                break;
            case "H":
                prevod = "Hujsa poskodba";
                break;
            case "S":
                prevod = "Smrt";
                break;
            case "U":
                prevod = "Neznano";
                break;
        }
        return prevod;
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(AccidentActitivty.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.37.1/diploma/public/api/accidents";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("accidents");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String accident = c.getString("id_nesrece");
                        String x = c.getString("x");
                        String y = c.getString("y");
                        String classfier = translateKlasNescerec(c.getString("klas_nesrece"));
                        String date = c.getString("datum");
                        String hour = c.getString("ura");


                        // Phone node is JSON Object
                        /*
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");
                        */
                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("accident", accident);
                        contact.put("classfier", classfier);
                        contact.put("date", date);
                        contact.put("hour", hour);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(AccidentActitivty.this, contactList,
                    R.layout.layout_accidents, new String[]{"accident", "classfier", "date", "hour"},
                    new int[]{R.id.accident, R.id.classfier, R.id.date, R.id.hour});
            lv.setAdapter(adapter);
        }
    }
}

