package com.example.abhishek.zomatowebservice;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {

    RecyclerView rv;
    LinearLayoutManager manager;
    MyTask myTask;
    MyAdapter myAdapter;
    ArrayList<Restaurant> arrayList;



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.row,
                    parent,false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Restaurant res = arrayList.get(position);
            String restaurant_name = res.getRestaurant_name();
            String restaurant_cuisine = res.getRestaurant_cuisines();
            String restaurant_address = res.getRestaurant_address();
            String restaurant_image = res.getRestaurant_image();
            String restaurant_latitude = res.getRestaurant_lat();
            String restaurant_longitude = res.getRestaurant_long();
            Float restaurant_rating = Float.parseFloat(res.getRestaurant_rating());

            holder.tv1.setText(restaurant_name);
            holder.tv2.setText(restaurant_cuisine);
            holder.tv3.setText(restaurant_address);
            holder.rb1.setRating(restaurant_rating);
            Glide.with(getActivity()).load(restaurant_image).into(holder.iv1);






        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tv1,tv2,tv3;
            public ImageView iv1,iv2;
            public RatingBar rb1;

            public ViewHolder(View itemView) {
                super(itemView);
                iv1 = itemView.findViewById(R.id.image);
                iv2=itemView.findViewById(R.id.imageView2);
                tv1= itemView.findViewById(R.id.textView1);
                tv2=itemView.findViewById(R.id.textView2);
                tv3=itemView.findViewById(R.id.textView3);
                rb1 = itemView.findViewById(R.id.ratingBar1);
                iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //get lat and long
                        int pos = getAdapterPosition();
                        Restaurant r = arrayList.get(pos);
                        String restaurant_name = r.getRestaurant_name();
                        double latitude = Double.parseDouble(r.getRestaurant_lat());
                        double longitude = Double.parseDouble(r.getRestaurant_long());
                        Intent in = new Intent(getActivity(),MapsActivity.class);
                        in.putExtra("latitude",latitude);
                        in.putExtra("longitude",longitude);
                        in.putExtra("name",restaurant_name);
                        getActivity().startActivity(in);

                    }
                });
            }
        }
    }


    public class MyTask extends AsyncTask<String,Void,String>{
        URL myurl;
        HttpURLConnection con;
        InputStream is;
        InputStreamReader reader;
        BufferedReader br;
        String str;
        StringBuilder sb;


        @Override
        protected String doInBackground(String... strings) {
            try {
                //a. prepare website url
                myurl = new URL(strings[0]);
                //b. open connection with server
                con = (HttpURLConnection) myurl.openConnection();
                //c. open connection for reading purpose (inputstream)
                con.setRequestProperty("Accept","application/json");//Ques-how to send request prperties to the server?
                //Ans- Use setRequestProperty on the connection object.

                //Q.what is the purpose of setRequest method
                //Ans.It will give extra info to server about the request eg. we can send
                //user name and password to the server using setRequest method, eg2
                //we can also tell whether we want json or xml to the server.eg3
                //We can also send API keys to the server for verification purspose
                con.setRequestProperty("user-key","f8fae1900b82dcd6ff4b324b33cef694");


                is = con.getInputStream();
                //d. open input stream reader
                reader = new InputStreamReader(is);
                //e. open buffered reader
                br = new BufferedReader(reader);
                //f. now using a do-while loop read data from bufr reader
                str = null;
                sb = new StringBuilder();
                do{
                    str = br.readLine();
                    sb.append(str);
                }while(str != null);
                //g. now we have complete server data in stringbuilder
                //convert stringbuilder to string and give to onpost execute
                return sb.toString();
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("B37","URL PROBLEM");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("B37","NETWORK PROBLEM.."+e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                Toast.makeText(getActivity(), "something wrong..see logcat", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject j = new JSONObject(s);
                JSONArray arr = j.getJSONArray("nearby_restaurants");
                for(int i = 0 ; i<arr.length(); i++){

                    JSONObject temp = arr.getJSONObject(i);
                    JSONObject restaurant = temp.getJSONObject("restaurant");
                    String restaurant_name = restaurant.getString("name");
                    JSONObject location = restaurant.getJSONObject("location");
                    String restaurant_address = location.getString("locality");
                    String restaurant_latitude = location.getString("latitude");
                    String restaurant_longitude = location.getString("longitude");
                    String restaurant_cuisine = restaurant.getString("cuisines");
                    String restaurant_image = restaurant.getString("thumb");
                    JSONObject user_rating = restaurant.getJSONObject("user_rating");
                    String restaurant_rating = user_rating.getString("aggregate_rating");

                    Restaurant res = new Restaurant(restaurant_name,restaurant_cuisine,restaurant_address,
                            restaurant_image,restaurant_latitude,restaurant_longitude,restaurant_rating);
                    arrayList.add(res);
                }
                Toast.makeText(getActivity(), "Added to arrayList", Toast.LENGTH_SHORT).show();
                myAdapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("B_37","JSON EXCEPTION"+e.getMessage());
            }

            super.onPostExecute(s);

            Toast.makeText(getActivity(), "Zomato result \n ", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().
                getSystemService(CONNECTIVITY_SERVICE);
        if(connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected() == true){
                return true; //we have internet connection
            }
        }
        return false; //we don't have internet connection
    }




    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        rv=v.findViewById(R.id.recyclerview1);
        manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        myTask = new MyTask();
        myAdapter= new MyAdapter();
        arrayList = new ArrayList<Restaurant>();
        rv.setLayoutManager(manager);
        rv.setAdapter(myAdapter);


        if(isNetworkAvailable()==false){
            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
        else{
            myTask.execute("https://developers.zomato.com/api/v2.1/geocode?lat=12.8984&lon=77.6179");
        }

        return v;
    }

}
