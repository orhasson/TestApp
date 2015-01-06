package com.pac.roman.myapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.codehaus.jackson.map.ObjectMapper;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import providers.APIActions;
import providers.CommonAction;
import users.ParentUser;

public class MainActivity extends Activity implements LocationListener {


    Button register;
    EditText userName;
    EditText password;
    EditText email;
    TextView textViewFromClient;
    public String registration;
    public String msgFromServer = "123";

    private static final String TAG = MainActivity.class.getName();
    static Location imHere; // Current location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        LocationListener locationListener = new MainActivity();
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        register = (Button) findViewById(R.id.regID);
        userName = (EditText) findViewById(R.id.fNameID);
        password = (EditText) findViewById(R.id.passwordID);
        email = (EditText) findViewById(R.id.emailID);

       // imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    public void sendLocationToServer(View view) {

    }

    public void onRegister(View view) throws IOException {
        ParentUser parentUser = new ParentUser();
        ArrayList<String> childrenIDs = new ArrayList<String>();
        childrenIDs.add("000");

        parentUser.setName(userName.getText().toString());
        parentUser.setPassword(password.getText().toString());
        parentUser.setEmail(email.getText().toString());
        parentUser.setChildrenIDs(childrenIDs);
        ObjectMapper objectMapper = new ObjectMapper();
        String parentAsString = objectMapper.writeValueAsString(parentUser);

        CommonAction commonAction = new CommonAction();
        commonAction.setAction(APIActions.ADD_PARENT);
        commonAction.setMsg(parentAsString);

        final String msgFromClient = objectMapper.writeValueAsString(commonAction);
        //Here client sends msgFromClient to server

        new Thread(new Runnable() {
            Socket client = new Socket();
            PrintWriter printwriter;

            @Override
            public void run() {
                try{
                    //connect to server
                    //establish socket connection to server
                    //write to socket using ObjectOutputStream
                    ObjectOutputStream oos = null;
                    ObjectInputStream ois = null;
                    client = new Socket("10.0.0.3", 7777);
                    oos = new ObjectOutputStream(client.getOutputStream());
                    System.out.println("Sending request to Socket Server");
                    oos.writeObject(msgFromClient);
                    oos.writeObject("exit");

                    //read the server response message
                    ois = new ObjectInputStream(client.getInputStream());
                    final String message = (String) ois.readObject();
                   /* runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewFromClient.setText(message);
                        }
                    });*/
                    //close resources
                    ois.close();
                    oos.close();
                    Thread.sleep(100);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
                /*try {
                    client = new Socket("192.168.44.1", 7777);  //connect to server
                    printwriter = new PrintWriter(client.getOutputStream(),true);
                    // printwriter.write("Altitude : " + imHere.getAltitude() + " , Latitude : " + imHere.getLatitude());  //write the message to output stream
                    printwriter.write(registration);  //write the message to output stream
                    printwriter.flush();

                    //Check out...
                    InputStream inputStream = client.getInputStream();
                    Scanner in = new Scanner(inputStream);
                    while(in.hasNext()) {
                        String line = in.nextLine();
                        msgFromServer = line;
                        //end connection......
                    }

                    client.close();   //closing the connection

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }).start();

    }



    @Override
    public void onLocationChanged(Location location) {
        imHere = location;

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}



