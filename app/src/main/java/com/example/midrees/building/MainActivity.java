package com.example.midrees.building;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    static public String input;                // To store the input of the input field
    private EditText flatNo;                   // Flat# edit text object
    private LinearLayout layout;               // Linear layout containing the login form
    private Button goButton;                   // Button that checks and gives access to user
    private TextInputLayout flatLayout;        // Animation shashke
    private CheckBox admin;
    private boolean isUser = false;
    //public static String ip = "127.0.0.1:81/building/";
    public static String ip = "192.168.10.12/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goButton = (Button) findViewById(R.id.go_btn);
        flatNo = (EditText) findViewById(R.id.flatno_editText);
        flatLayout = (TextInputLayout) findViewById(R.id.input_layout_flat);
        admin =(CheckBox) findViewById(R.id.checkbox);

        /**
         *  If the user is not an admin than give access directly
         *  else prompt the user for password
         */
        goButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override

            public void onClick(View view) {

                layout = (LinearLayout) findViewById(R.id.main_linear_layout);
                goButton = (Button) findViewById(R.id.go_btn);

                input = flatNo.getText().toString().trim();
                final String psd = "";
                final String type = "login";

                if(admin.isChecked()){
                    isUser = true;
                }
                else
                    isUser = false;

                BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                try {
                    backgroundWorker.execute(type, input, psd).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if (isUser){
                    // Enter the edit text for password
                    final EditText pass = new EditText(getApplicationContext());
                    pass.setHint("Password");
                    pass.setTextSize(32);
                    pass.setGravity(Gravity.CENTER);
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    pass.setPadding(16,16,16,16);
                    pass.setTextColor(goButton.getHintTextColors());

                    // Remove button and than add a new button below the password field
                    //  goButton.setGravity(0);
                    layout.removeView(goButton);
                    layout.addView(pass);
                    layout.addView(goButton);

                    goButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String password = pass.getText().toString().trim();
                            BackgroundWorker backgroundWorker = new BackgroundWorker(MainActivity.this);
                            backgroundWorker.execute(type, input, password);
                        }
                    });
                }
                }
        });
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
