package com.notexample.austin.questicon;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class CharacterActivity extends AppCompatActivity {

//    ArrayList<CharacterModel> characterModels;
//    CustomAdapterCharacter adapter;
//    ListView listView;
    EditText realm, charactername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

//        listView = (ListView) findViewById(R.id.listView);
//        realm = (EditText) findViewById(R.id.realm);
//        charactername = (EditText) findViewById(R.id.charactername);
//        String searchVariableName = charactername.getText().toString();
//         String searchVariableRealm = realm.getText().toString();

//
//        characterModels = new ArrayList<>();
//        adapter = new CustomAdapterCharacter(this, characterModels);
//        listView.setAdapter(adapter);


        CheckingInternetConnection();


    }


    public void CheckingInternetConnection() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {


            Intent intent1 = new Intent(this, CharacterActivity.class);

            PendingIntent pendingIntent1 = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);


            NotificationCompat.BigTextStyle bigPictureStyle = new NotificationCompat.BigTextStyle();
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(android.R.drawable.star_on);
            mBuilder.setContentTitle("Welcome to Questicon");
            mBuilder.setContentIntent(pendingIntent1);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigPictureStyle);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, bigPictureStyle.build());
            notificationManager.cancel(6);


        } else {
            Toast.makeText(getApplicationContext(), "Connection not ready",
                    Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(Settings.ACTION_WIFI_SETTINGS);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent1, 0);


            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setSummaryText("To use the app, please enable WIFI, Thanks!");

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(android.R.drawable.star_on);
            mBuilder.setContentTitle("Questicon Needs Attention");
            mBuilder.setContentText("To use the Question, you'll need to enable WIFI.");
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setPriority(Notification.PRIORITY_MAX);
            mBuilder.setStyle(bigTextStyle);
            mBuilder.addAction(R.drawable.wifi, "To open WIFI settings", pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(6, bigTextStyle.build());
            notificationManager.cancel(1);


        }
    }




//    public void APICALL() {
//
//        final AsyncHttpClient client = new AsyncHttpClient();
//
//        final String searchVariableName = charactername.getText().toString();
//        final String searchVariableRealm = realm.getText().toString();
//
//
//        // Not sure why this boolean isn't working but the objective was to make it so that if a user enter's nothing, the search doesn't happen
//
//
//        client.get("https://us.api.battle.net/wow/character/" + searchVariableRealm + "/" + searchVariableName + "?fields=appearance&locale=en_US&apikey=wheces9zargz65mhza5jfv9nentuy2gg\n", new JsonHttpResponseHandler() {
//
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, final JSONObject responseBody) {
//
//
//                try {
//                    String name = responseBody.getString("name");
//                    String battlegroup = responseBody.getString("battlegroup");
//                    String newRaceName = "";
//                    String newClassName = "";
//                    String newFaction = "";
//                    String newGender = "";
//                    final String image = responseBody.getString("thumbnail");
//                    int classWow = responseBody.getInt("class");
//                    int race = responseBody.getInt("race");
//                    int gender = responseBody.getInt("gender");
//                    int ap = responseBody.getInt("achievementPoints");
//                    int faction = responseBody.getInt("faction");
//                    int level = responseBody.getInt("level");
//                    int kills = responseBody.getInt("totalHonorableKills");
//
//
//                    CharacterModel character = new CharacterModel(name, battlegroup, image, classWow, race, gender, ap, faction, level, kills, newRaceName, newClassName, newFaction, newGender);
//
//
//                    switch (gender) {
//                        case 0:
//                            character.setNewGender("Male");
//                            break;
//                        case 1:
//                            character.setNewGender("Female");
//                            break;
//                        case 2:
//                            character.setNewGender("Unisex");
//                            break;
//                        default:
//                            character.setNewGender("No gender found");
//                            break;
//                    }
//
//
//                    switch (race) {
//                        case 1:
//                            character.setNewClassName("Warrior");
//                            break;
//                        case 2:
//                            character.setNewClassName("Paladin");
//                            break;
//                        case 3:
//                            character.setNewClassName("Hunter");
//                            break;
//                        case 4:
//                            character.setNewClassName("Rogue");
//                            break;
//                        case 5:
//                            character.setNewClassName("Priest");
//                            break;
//                        case 6:
//                            character.setNewClassName("Death Knight");
//                            break;
//                        case 7:
//                            character.setNewClassName("Shaman");
//                            break;
//                        case 8:
//                            character.setNewClassName("Mage");
//                            break;
//                        case 9:
//                            character.setNewClassName("Warlock");
//                            break;
//                        case 10:
//                            character.setNewClassName("Monk");
//                            break;
//                        case 11:
//                            character.setNewClassName("Druid");
//                            break;
//                        default:
//                            character.setNewClassName("No class found");
//                            break;
//                    }
//
//                    switch (faction) {
//                        case 0:
//                            character.setNewFaction("Alliance");
//                            break;
//                        case 1:
//                            character.setNewFaction("Horde");
//                            break;
//                        default:
//                            character.setNewFaction("No faction found");
//                            break;
//                    }
//
//
//                    switch (classWow) {
//                        case 1:
//                            character.setNewRaceName("Human");
//                            break;
//                        case 2:
//                            character.setNewRaceName("Orc");
//                            break;
//                        case 3:
//                            character.setNewRaceName("Dwarf");
//                            break;
//                        case 4:
//                            character.setNewRaceName("Night Elf");
//                            break;
//                        case 5:
//                            character.setNewRaceName("Undead");
//                            break;
//                        case 6:
//                            character.setNewRaceName("Tauren");
//                            break;
//                        case 7:
//                            character.setNewRaceName("Gnome");
//                            break;
//                        case 8:
//                            character.setNewRaceName("Troll");
//                            break;
//                        case 9:
//                            character.setNewRaceName("Goblin");
//                            break;
//                        case 10:
//                            character.setNewRaceName("Blood Elf");
//                            break;
//                        case 11:
//                            character.setNewRaceName("Draenei");
//                            break;
//                        case 22:
//                            character.setNewRaceName("Worgen");
//                            break;
//                        case 24:
//                            character.setNewRaceName("Pandaren");
//                            break;
//                        case 25:
//                            character.setNewRaceName("Pandaren");
//                            break;
//                        case 26:
//                            character.setNewRaceName("Pandaren");
//                            break;
//                        default:
//                            character.setNewRaceName("No race found");
//                            break;
//                    }
//
//                    String imageThumb = responseBody.getString("thumbnail");
//
//
//                    ArrayList<CharacterModel> characterModels = new ArrayList<>();
//                    CustomAdapterCharacter adapter = new CustomAdapterCharacter(CharacterActivity.this, characterModels);
//
//                    listView.setAdapter(adapter);
//
//
//                    adapter.add(character);
//
//                    adapter.notifyDataSetChanged();
//
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent myIntent = new Intent(CharacterActivity.this, CharacterDetailView.class);
//                            myIntent.putExtra("position", position);
//
//
//                            try {
//
//                                String image = responseBody.getString("thumbnail");
//                                myIntent.putExtra("url2", image);
//                                startActivity(myIntent);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    });
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Toast.makeText(getApplicationContext(), "No character under the name: " + searchVariableName + " found.",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }

    public void clickingSearch(View view) {


//        APICALL();
        SearchWithWebview();

    }


    public void SearchWithWebview(){
        realm = (EditText) findViewById(R.id.realm);
        charactername = (EditText) findViewById(R.id.charactername);
        String searchVariableName = charactername.getText().toString();
        String searchVariableRealm = realm.getText().toString();

        WebView webViewCharacter = (WebView) findViewById(R.id.webViewChacter);


        final ProgressDialog pd = ProgressDialog.show(this, "", "Loading...", true);
        webViewCharacter.getSettings().setBuiltInZoomControls(true);
        webViewCharacter.getSettings().setSupportZoom(true);
        webViewCharacter.setInitialScale(100);
        webViewCharacter.getSettings().getLoadsImagesAutomatically();
        webViewCharacter.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webViewCharacter.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.setMessage("Loading pet your character, just one second more...");
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

        webViewCharacter.loadUrl("http://us.battle.net/wow/en/character/"+searchVariableRealm+"/"+searchVariableName+"/advanced");



    }
}










