package e.mrarifin.cobaeresep;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import e.mrarifin.cobaeresep.FoodList;
import e.mrarifin.cobaeresep.MainActivity;


public class Menu extends AppCompatActivity {
    private CardView lihatnilai,profil,lihatnilai1,profil1;
    SharedPreferences sharedpreferences;
    String id, username;
    TextView txt_username;
    Button btn_logout;
    public static final String TAG_USERNAME = "username";
    public static final String TAG_ID = "id";
    private static  final int TIME_INTERVAL=2000;
    private long mBackPressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        lihatnilai=(CardView)findViewById(R.id.Lihatnilai);
        profil=(CardView)findViewById(R.id.profil);
        lihatnilai1=(CardView)findViewById(R.id.Lihatnilai1);
        profil1=(CardView)findViewById(R.id.profil1);


        lihatnilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, FoodList.class);
                finish();
                startActivity(intent);
            }
        });
        lihatnilai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, DrinkMainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        profil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, DrinkFoodList.class);
                finish();
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getBaseContext(), "Tekan Back Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT).show();}
        mBackPressed=System.currentTimeMillis();
        finish();

    }


}
