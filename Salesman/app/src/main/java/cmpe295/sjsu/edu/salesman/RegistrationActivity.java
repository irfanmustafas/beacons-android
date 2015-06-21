package cmpe295.sjsu.edu.salesman;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class RegistrationActivity extends ActionBarActivity {

    private EditText first;
    private EditText last;
    private EditText password;
    private EditText email;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupVariables();
    }

    public void setupVariables(){
        first = (EditText) findViewById(R.id.editText_first);
        last = (EditText) findViewById(R.id.editText_last);
        password = (EditText) findViewById(R.id.editText_password);
        email = (EditText) findViewById(R.id.editText_email);
        register = (Button) findViewById(R.id.registerBtn);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Create user
    //This is giving back the correct json but still not  a success.because we have to make two internal rest calls one for
    // sending the email to user and other with email verified.
    public void createUser(View view) {
        String firstStr = first.getText().toString();
        String lastStr = last.getText().toString();
        String pwd = password.getText().toString();
        String emailStr = email.getText().toString();
        Name name1 = new Name();
        User user1 = new User();


        name1.setFirst(firstStr);
        name1.setLast(lastStr);
        user1.setName(name1);
        user1.setPassword(pwd);
        user1.setEmail(emailStr);



        System.out.println("User is::" + firstStr + lastStr + pwd + emailStr);

        RestClient.get().registerUser(user1, new Callback<RegisterUserResponse>() {
            @Override
            public void success(RegisterUserResponse registerUserResponse, Response response) {

                RestClient.get().getUserStatus(registerUserResponse.getUserId(), new Callback<userStatusResponse>() {
                    @Override
                    public void success(userStatusResponse userStatusResponse, Response response) {
                        RestClient.get().verifyUser(userStatusResponse.getUserId(), new Callback<String>() {
                            @Override
                            public void success(String s, Response response) {
                                System.out.println("user created!!");
                                navigatetoHomeActivity();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                System.out.println("user nt verified!!");
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("User creation errro");
                error.getCause();

                Toast.makeText(getApplicationContext(), "Sorry not able to create a user!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method which navigates from Registration Activity to Home Activity
     */
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),HomeActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    /**
     * Method which navigates from Registration Screen to Offer Screen
     */
    public void offerDetails(View view){
        Intent offerDetailsIntent = new Intent(getApplicationContext(),OfferActivity.class);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(offerDetailsIntent);
    }
}
