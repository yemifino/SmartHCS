package com.example.sasha.smarthcs;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Card> cards = new ArrayList<>();
    static ArrayList<Card2> cards2 = new ArrayList<>();
    static ArrayList<User> user_base = new ArrayList<>();
    static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addUsers();
        Button loginButton = findViewById(R.id.login_button);
        final EditText loginText = findViewById(R.id.login_text);
        final EditText passwordText = findViewById(R.id.password_text);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cur_name = loginText.getText().toString();
                String cur_password = passwordText.getText().toString();
                ArrayList<User> users = user_base;
                Boolean ok = false;
                for(int i = 0; i < users.size(); i++) {
                    if(cur_name.equals(users.get(i).getLogin()) && cur_password.equals(users.get(i).getPassword())) {
                        index = i;
                        upd();
                        openProfile();
                        ok = true;
                    }
                }
                if (!ok)
                {
                    Toast.makeText(getApplicationContext(), "Ошибка, повторите ввод", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Успешно", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void openProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public static ArrayList<Bill> addHistory() {
        ArrayList<Bill> history = new ArrayList<>();
        int n = Math.max((int)(Math.random() * 20), 5);
        for (int i = 0; i < n; i++) history.add(new Bill((int)(Math.random() * 1003 + 100), (int)(Math.random() * 1003 + 100), (int)(Math.random() * 1003 + 100)));
        return history;
    }

    public static void addUsers() {
        user_base.add(new User("LOlI", "123", addHistory()));
        user_base.add(new User("47th-Draganov", "123", addHistory()));
        user_base.add(new User("karasek", "i_love_loli", addHistory()));
        user_base.add(new User("hyper_serrriy", "123", addHistory()));
    }

    public static void upd()
    {
        cards.clear();
        cards2.clear();
        int j = index, year = 2019, month = 10;
        for (int i = user_base.get(j).getHistory().size() - 1; i >= 0; i--)
        {
            Bill last = user_base.get(j).getHistory().get(i);
            month--;
            if (month == 0) {
                year--;
                month = 12;
            }
            cards2.add(new Card2(year, month, last));
        }
        User user = user_base.get(j);
        Bill last = user.getHistory().get(user.getHistory().size() - 1);
        cards.add(new Card("Вода", last.sum_w));
        cards.add(new Card("Газ", last.sum_g));
        cards.add(new Card("Электричество", last.sum_l));
        cards.add(new Card("Итого", last.sum_l + last.sum_w + last.sum_g));
    }

    public static double water_resurse(int cost)
    {
        double res = (double)(cost) / 100;
        return res + 0.005;
    }

    public static double gas_resurse(int cost)
    {
        double res = (double)(cost) / 6.5;
        return res + 0.005;
    }

    public static double light_resurse(int cost)
    {
        double res = (double)(cost) / 500;
        return res + 0.005;
    }

}
