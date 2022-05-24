package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShopScreen extends AppCompatActivity implements MySecondAdapter.OnNoteListener {

    private Button addToCartButton;

    private RequestQueue requestQueue;

    private int count;

    RecyclerView recyclerView;

    ArrayList<String> s1 = new ArrayList<>();
    ArrayList<Double> pricelist = new ArrayList<>();
    ArrayList<String> imagesEncoded = new ArrayList<>();

    ArrayList<Bitmap> images = new ArrayList<>();

    ArrayList<String> checkCartItems = new ArrayList<>();
    ArrayList<Integer> checkCartQty = new ArrayList<>();

    private static int itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        count = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_screen);
        addToCartButton = findViewById(R.id.btnAdd);

//        images.add(R.drawable.breadpic);
//        images.add(R.drawable.breadpic);
//        images.add(R.drawable.breadpic);
//        images.add(R.drawable.breadpic);
//        images.add(R.drawable.breadpic);

        requestQueue = Volley.newRequestQueue(this);
        String requestURL = makeURL();
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        System.out.println(response);
                        boolean finished = false;
                        int index = 0;
                        while(!finished){
                            try {
                                System.out.println(response);
                                JSONObject curObject = response.getJSONObject(index);
                                String curCat = curObject.getString("name");
                                s1.add(curCat);
                                double curPrice = curObject.getDouble("price");
                                pricelist.add(curPrice);
                                String curImage = curObject.getString("ItemImage");
                                imagesEncoded.add(curImage);
                                index++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finished = true;
                                decode();
                                System.out.println(pricelist + "hiere kijken");
                                setAdapter();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //txtResponse.setText( error.getLocalizedMessage() );
                    }
                }
        );
        requestQueue.add(submitRequest);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView = findViewById(R.id.scndRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void decode() {
        for (int i = 0; i < s1.size(); i++) {
            //System.out.println(s1.get(i));
            byte[] decodedString = Base64.decode(imagesEncoded.get(i), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            images.add(decodedByte);
        }
    }

    private String makeURL() {
        String cat = FirstFragment.getArrayList().get(FirstFragment.getCategoryID());
        return "https://studev.groept.be/api/a21pt115/getItems/" + cat;
    }

    private String makeURL2(){
        String name = s1.get(itemID);
        String price = Double.toString(pricelist.get(itemID));
        return "https://studev.groept.be/api/a21pt115/insertTest/" + name + "/" + price;
    }

    private String makeURLinc(){
        String name = s1.get(itemID);
        return "https://studev.groept.be/api/a21pt115/increaseItemInCart/" + name;
    }

    private String makeURLincPrice(){
        String name = s1.get(itemID);
        return "https://studev.groept.be/api/a21pt115/increasePriceInCart/" + name;
    }

    public void setAdapter() {
        //MySecondAdapter mySecondAdapter = new MySecondAdapter(this, s1, images, pricelist, this);
        MySecondAdapter mySecondAdapter = new MySecondAdapter(this,s1,images,pricelist,this);
        recyclerView.setAdapter(mySecondAdapter);
    }

    private void postDataUsingVolley(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    @Override
    public void onNoteClick(int position) {
        itemID = position;
        if(count >= s1.size()) {
            checkCart();
            Toast.makeText(this, s1.get(position) + " has been added to your cart", Toast.LENGTH_SHORT).show();
        }
        else{
            count++;
        }
    }

    private void addNew() {
        postDataUsingVolley(makeURL2());
    }

    private void increase() {
        postDataUsingVolley(makeURLinc());
        postDataUsingVolley(makeURLincPrice());
    }

    private void checkCart() {
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt115/getCart";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        checkCartItems = new ArrayList<>();
                        checkCartQty = new ArrayList<>();
                        System.out.println(response);
                        boolean finished = false;
                        int index = 0;
                        while(!finished){
                            try {
                                System.out.println(response);
                                JSONObject curObject = response.getJSONObject(index);
                                String curCat = curObject.getString("item");
                                checkCartItems.add(curCat);
                                int curQty = curObject.getInt("quantity");
                                checkCartQty.add(curQty);
                                index++;
                                //System.out.println(curCat);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finished = true;
                                System.out.println(checkCartQty + "na de db loop");
                                System.out.println(checkCartItems + "na de db loop");
                                if(checkCartItems.contains(s1.get(itemID))){
                                    increase();
                                }
                                else{
                                    addNew();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //txtResponse.setText( error.getLocalizedMessage() );
                    }
                }
        );
        requestQueue.add(submitRequest);
    }
}
