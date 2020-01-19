package eu.ase.bilet1restantaarticol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_ARTICLE_CODE = 200;
    public static final String ADD_ARTICLE_TO_LIST = "addArticleToList";
    private ArrayList<Articol> articole = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Toast.makeText(getApplicationContext(), R.string.author,
                    Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.add_article) {
            Intent intent = new Intent(getApplicationContext(), AddArticleActivity.class);
            startActivityForResult(intent, REQUEST_ADD_ARTICLE_CODE);
        }
        if(item.getItemId() == R.id.list_articles){
            if(articole != null){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putParcelableArrayListExtra(ADD_ARTICLE_TO_LIST, articole);
                startActivity(intent);
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_ARTICLE_CODE && resultCode == RESULT_OK && data != null) {
            Articol articol = data.getParcelableExtra(AddArticleActivity.ADD_ARTICLE_KEY);
            if (articol != null) {
                articole.add(articol);
                Toast.makeText(getApplicationContext(), articol.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
