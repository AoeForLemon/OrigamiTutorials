package io.github.veroz.origamitutorials.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.veroz.origamitutorials.MainActivity;
import io.github.veroz.origamitutorials.R;
import io.github.veroz.origamitutorials.adapter.OrigamiArrayAdapter;
import io.github.veroz.origamitutorials.constant.JsonConstants;
import io.github.veroz.origamitutorials.model.OrigamiBean;
import io.github.veroz.origamitutorials.ui.activity.PlayVideoActivity;
import io.github.veroz.origamitutorials.ui.activity.ViewPictureActivity;
import io.github.veroz.origamitutorials.utils.HttpHandler;
import io.github.veroz.origamitutorials.utils.InternetConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private static final String TAG = "VideoFragment";
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<OrigamiBean> origamiBeanArrayList;
    private OrigamiArrayAdapter origamiArrayAdapter;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.fragment_video, container, false);

        listView = frameLayout.findViewById(R.id.list_video);

        swipeRefreshLayout = (SwipeRefreshLayout) frameLayout.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setDistanceToTriggerSync(250);

        return frameLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        origamiBeanArrayList = new ArrayList<OrigamiBean>();
        origamiArrayAdapter = new OrigamiArrayAdapter(getContext(), origamiBeanArrayList);

        listView.setAdapter(origamiArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), new StringBuilder().append("id:" + origamiBeanArrayList.get(position).getId()).append("\n").append("name:" + origamiBeanArrayList.get(position).getName()).append("\n").append("isImg:" + origamiBeanArrayList.get(position).getIsImg()).append("\n").append("url:" + origamiBeanArrayList.get(position).getUrl()).append("\n").append("pic:" + origamiBeanArrayList.get(position).getPic()).append("\n").append("description:" + origamiBeanArrayList.get(position).getDescription()).append("\n").append("details:size:" + origamiBeanArrayList.get(position).getDetails().getSize()).append("\n").append("details:date:" + origamiBeanArrayList.get(position).getDetails().getDate()), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.mainActivity, PlayVideoActivity.class);
                intent.putExtra("url", origamiBeanArrayList.get(position).getUrl());
                startActivity(intent);
                ((Activity) MainActivity.mainActivity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.mainActivity, ViewPictureActivity.class);
                intent.putExtra("name", origamiBeanArrayList.get(position).getName());
                intent.putExtra("pic", origamiBeanArrayList.get(position).getPic());
                startActivity(intent);
                ((Activity) MainActivity.mainActivity).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                executeRefresh();

            }
        });

        executeRefresh();

    }

    private void executeRefresh() {

        Boolean isNetworkConnected = InternetConnection.checkConnection(getContext());

        if (isNetworkConnected) {
            new GetDataTask().execute();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), "Internet connection:" + isNetworkConnected, Toast.LENGTH_SHORT).show();
        }

    }

    class GetDataTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (origamiBeanArrayList.size() > 0) {
                origamiBeanArrayList.clear();
                //listView.setAdapter(origamiArrayAdapter);
                origamiArrayAdapter.notifyDataSetChanged();
            }

            swipeRefreshLayout.setRefreshing(true);
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            /**
             * Getting JSON Object from Web Using HttpHandler
             */
            String jsonString = new HttpHandler().getDataFromUrl("https://raw.githubusercontent.com/AoeForLemon/OrigamiTutorialsData/master/aoe_data.json");

            Log.w(TAG, "Response from url:" + jsonString);

            // Check jsonString is not null
            if (jsonString != null) {

                try {

                    // Covert jsonString to a JSONObject object
                    JSONObject jsonObject = new JSONObject(jsonString);

                    // Check jsonObject is not null
                    if (jsonObject != null) {

                        // Check Length of jsonObject
                        if (jsonObject.length() > 0) {

                            /**
                             * Getting Array named "datas" From MAIN Json Object
                             */
                            JSONArray datas = jsonObject.getJSONArray(JsonConstants.KEY_DATAS);

                            // Check Length of datas Array
                            int lenArray = datas.length();
                            if (lenArray > 0) {

                                for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                                    /**
                                     * Creating Every time New Object
                                     * and
                                     * Adding into List
                                     */
                                    OrigamiBean origamiBean = new OrigamiBean();

                                    /**
                                     * Getting Inner Object from datas array...
                                     * and
                                     * From that We will get Name of that Data
                                     */
                                    JSONObject innerObject = datas.getJSONObject(jIndex);

                                    String id = innerObject.getString(JsonConstants.KEY_ID);
                                    String name = innerObject.getString(JsonConstants.KEY_NAME);
                                    Boolean isimg = innerObject.getBoolean(JsonConstants.KEY_ISIMG);
                                    String url = innerObject.getString(JsonConstants.KEY_URL);
                                    String pic = innerObject.getString(JsonConstants.KEY_PIC);
                                    String description = innerObject.getString(JsonConstants.KEY_DESCRIPTION);

                                    origamiBean.setId(id);
                                    origamiBean.setName(name);
                                    origamiBean.setIsImg(isimg);
                                    origamiBean.setUrl(url);
                                    origamiBean.setPic(pic);
                                    origamiBean.setDescription(description);

                                    /**
                                     * Getting Object from Object "details"
                                     */
                                    JSONObject detailsObject = innerObject.getJSONObject(JsonConstants.KEY_DETAILS);

                                    String size = detailsObject.getString(JsonConstants.KEY_SIZE);
                                    String date = detailsObject.getString(JsonConstants.KEY_DATE);

                                    OrigamiBean.DetailsBean detailsBean = new OrigamiBean.DetailsBean();

                                    detailsBean.setSize(size);
                                    detailsBean.setDate(date);

                                    origamiBean.setDetails(detailsBean);

                                    /**
                                     * Adding datas and details in List...
                                     */
                                    origamiBeanArrayList.add(origamiBean);

                                }

                            }

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.i(TAG, "JSONParser:" + e.getLocalizedMessage());
                }

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing() || swipeRefreshLayout.isRefreshing()) {
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }

            /**
             * Checking if List size if more than zero then
             * Update ListView
             */
            if (origamiBeanArrayList.size() > 0) {
                origamiArrayAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "datas is empty", Toast.LENGTH_SHORT).show();
            }

        }

    }

}
