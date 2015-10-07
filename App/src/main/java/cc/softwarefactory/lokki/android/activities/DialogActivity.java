package cc.softwarefactory.lokki.android.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cc.softwarefactory.lokki.android.MainApplication;
import cc.softwarefactory.lokki.android.services.LocationService;

/**
 * Created by kapoor on 6.10.2015.
 */
public class DialogActivity extends AppCompatActivity{
public static final String CURRENT_PLACEID = "place";
private static final String TAG="DialogActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String placeid=intent.getStringExtra(CURRENT_PLACEID);
        String placename="";
       try{  placename = MainApplication.places.getJSONObject(placeid).getString("name");}
       catch (JSONException e)
       {
           Log.e(TAG,"Error while retrieving place " + e);
           finish();
       }

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,int which){
                        for (int i=0;i<MainApplication.buzzPlaces.length();i++)
                        {
                            try {
                                JSONObject buzzObject = MainApplication.buzzPlaces.getJSONObject(i);
                                if (buzzObject.getString("placeid").equals(placeid)) {
                                    buzzObject.put("buzzcount",0);

                                }
                            }
                            catch (JSONException e)
                            {
                                Log.e(TAG, "Error while checking place " + e);

                            }
                        }
                        finish();
                    }
                })
                .setTitle("You have arrived in "+placename)

                .create();




        alertDialog.show();
        LocationService.start(this.getApplicationContext());
    }
}
