package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;



public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        ImageView imageView = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView originView = findViewById(R.id.origin_tv);
        originView.setText(sandwich.getPlaceOfOrigin());
        TextView descriptionView = findViewById(R.id.description_tv);
        descriptionView.setText(sandwich.getDescription());

        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        String ingredientstr = "";

        for(String ingredient :sandwich.getIngredients()){

            ingredientstr = ingredientstr.concat(ingredient.concat("\n"));

        }
        if(ingredientstr != null) {

            ingredientsView.setText(ingredientstr.substring(0, ingredientstr.length()));
        }

        TextView also_known_View = findViewById(R.id.also_known_tv);

        String alsoKnownAsStr = "";

        for(String alsoKnownAs : sandwich.getAlsoKnownAs()){
            alsoKnownAsStr = alsoKnownAsStr.concat(alsoKnownAs.concat("\n"));
        }
        if(alsoKnownAsStr != null) {
            also_known_View.setText(alsoKnownAsStr.substring(0, alsoKnownAsStr.length()));
        }



    }
}
