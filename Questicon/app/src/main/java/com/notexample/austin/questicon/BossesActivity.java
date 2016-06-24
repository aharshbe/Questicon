package com.notexample.austin.questicon;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class BossesActivity extends AppCompatActivity {
    ArrayList<BossesModel> bossesModels;
    CustomAdapterBosses adapterBosses;
    String theySearched = "";
    ArrayList<BossesModel> bossesModels3;
    View.OnClickListener mOnClickListener;
    TextToSpeech textToSpeech;
    int postitionTHis;
    JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler() {


        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {


            try {
                JSONArray jsonArray = responseBody.getJSONArray("bosses");


                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject bosses = jsonArray.getJSONObject(i);

                    if (!bosses.has("description") || !bosses.has(("name")) || !bosses.has(("level")) || !bosses.has(("heroicHealth")) || !bosses.has(("journalId")))
                        continue;
                    final BossesModel bossesModel = new BossesModel(bosses.get("name").toString(), bosses.get("description").toString(),
                            bosses.get("level").toString(), bosses.get("heroicHealth").toString(),
                            bosses.get("journalId").toString());
                    bossesModels.add(bossesModel);
                    adapterBosses.notifyDataSetChanged();


                    final ListView listViewBosses = (ListView) findViewById(R.id.listViewBosses);


                    assert listViewBosses != null;
                    listViewBosses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                            postitionTHis = position;

                            if (theySearched.equalsIgnoreCase("")) {

                                final BossesModel boss = bossesModels.get(position);


                                Snackbar.make(findViewById(android.R.id.content), "Speaking text from: " + boss.getName(), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Stop speaking", mOnClickListener)
                                        .setActionTextColor(Color.RED)
                                        .show();

                                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {

                                        if (status != TextToSpeech.ERROR) {
                                            textToSpeech.setLanguage(Locale.UK);

                                            textToSpeech.speak(boss.getDescription().toString(),
                                                    TextToSpeech.QUEUE_FLUSH, null);


                                        }
                                    }
                                });
                            }else {
                                final BossesModel boss = bossesModels3.get(position);


                                Snackbar.make(findViewById(android.R.id.content), "Speaking text from: " + boss.getName(), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Stop speaking", mOnClickListener)
                                        .setActionTextColor(Color.RED)
                                        .show();

                                textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {

                                        if (status != TextToSpeech.ERROR) {
                                            textToSpeech.setLanguage(Locale.UK);

                                            textToSpeech.speak(boss.getDescription().toString(),
                                                    TextToSpeech.QUEUE_FLUSH, null);


                                        }
                                    }
                                });

                            }


                        }
                    });

                    mOnClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textToSpeech.stop();
                        }
                    };

                    listViewBosses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                       int pos, long arg3) {
                            if (theySearched.equalsIgnoreCase("")) {

                                BossesModel boss = bossesModels.get(pos);

                                Intent myIntent = new Intent(BossesActivity.this, BossesDetailView.class);
                                myIntent.putExtra("position", pos);
                                myIntent.putExtra("des", boss.getDescription());
                                myIntent.putExtra("name", boss.getName());

                                startActivity(myIntent);


                            } else {
                                BossesModel boss = bossesModels3.get(pos);

                                Intent myIntent = new Intent(BossesActivity.this, BossesDetailView.class);
                                myIntent.putExtra("position", pos);
                                myIntent.putExtra("des", boss.getDescription());
                                myIntent.putExtra("name", boss.getName());

                                startActivity(myIntent);

                            }


                            return false;
                        }
                    });
                }
                Toast.makeText(BossesActivity.this, "There are "+jsonArray.length()+ " bosses to search through.", Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Toast.makeText(getApplicationContext(), "Process Not Successful",
                    Toast.LENGTH_SHORT).show();
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bosses);


        bosses();

        handleIntent(getIntent());

        checkFirstRun();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hello!");
        builder.setIcon(R.drawable.gyro_animation);
        builder.setMessage("Loading Bosses, just one second");
        builder.setCancelable(false);

        final AlertDialog closedialog= builder.create();

        closedialog.show();

        final Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                closedialog.dismiss();
                timer2.cancel(); //this will cancel the timer of the system
            }
        }, 2000);


    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {


        }
    }


    public void bosses() {

        ListView listViewBosses = (ListView) findViewById(R.id.listViewBosses);
        bossesModels = new ArrayList<>();
        adapterBosses = new CustomAdapterBosses(this, bossesModels);
        listViewBosses.setAdapter(adapterBosses);


        final AsyncHttpClient client = new AsyncHttpClient();


        // Not sure why this boolean isn't working but the objective was to make it so that if a user enter's nothing, the search doesn't happen


        client.get("https://us.api.battle.net/wow/boss/?locale=en_US&apikey=wheces9zargz65mhza5jfv9nentuy2gg", jsonHttpResponseHandler);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info, menu);
        inflater.inflate(R.menu.options_menu, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                theySearched = newText;

                bossesModels3 = new ArrayList<>();

                for (int i = 0; i < bossesModels.size(); i++) {

                    if (bossesModels.get(i).getName().toLowerCase().contains(newText.toLowerCase())) {
                        bossesModels3.add(bossesModels.get(i));
                    }

                }
                ListView listViewBosses = (ListView) findViewById(R.id.listViewBosses);
                bossesModels.contains(newText);
                adapterBosses = new CustomAdapterBosses(BossesActivity.this, bossesModels3);
                listViewBosses.setAdapter(adapterBosses);

                adapterBosses.notifyDataSetChanged();


                return false;
            }
        });


        return true;
    }


    public void clickingInfo(MenuItem item) {
        InfoDiaglogue();

    }
    public void InfoDiaglogue() {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("So you're a little confused...");
        builder2.setIcon(R.mipmap.ic_launcher_questicon);
        builder2.setCancelable(true);
        builder2.setMessage("A single press on a boss:\n\n Will start the text to speech engine and read that bosses lore. \n\n A long press on a boss: \n\n Will open that bosses detailed informtaion like loot gained from killing the boss or which dungeon you can find him/her/it in.");
        builder2.setPositiveButton(
                "Thanks!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                        return;
                    }
                });

        AlertDialog alert12 = builder2.create();
        alert12.show();
    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun2", true);
        if (isFirstRun) {

            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setTitle("Dear WoW player...");
            builder2.setIcon(R.mipmap.ic_launcher_questicon);
            builder2.setCancelable(true);
            builder2.setMessage("A single press on a boss:\n\n Will start the text to speech engine and read that bosses lore. \n\n A long press on a boss: \n\n Will open that bosses detailed informtaion like loot gained from killing the boss or which dungeon you can find him/her/it in.");
            builder2.setPositiveButton(
                    "Thanks!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                            return;
                        }
                    });

            AlertDialog alert12 = builder2.create();
            alert12.show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun2", false)
                    .apply();
        }
    }
}



