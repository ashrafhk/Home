package com.example.ashrafhussain.home;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Blank2Fragment extends Fragment {

    private RecyclerView       mRecyclerView;
    private EditText           mEditText;
    private Button             mFetchFeedButton;
    private SwipeRefreshLayout mSwipeLayout;
    private TextView           mFeedTitleTextView;
    private TextView           mFeedLinkTextView;
    private TextView           mFeedDescriptionTextView;


    private List<RssFeedModel> mFeedModelList;
    private String             mFeedTitle;
    private String             mFeedLink;
    private String             mFeedDescription;

    private String TAG;

    public Blank2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blank2, container, false);
        mRecyclerView           = (RecyclerView) v.findViewById(R.id.recyclerView);
        mEditText               = (EditText) v.findViewById(R.id.webapiFeedEditText);
        mFetchFeedButton        = (Button) v.findViewById(R.id.fetchFeedButton);
        mSwipeLayout            = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        mFeedTitleTextView      = (TextView) v.findViewById(R.id.feedTitle);
        mFeedDescriptionTextView = (TextView) v.findViewById(R.id.feedDescription);
        mFeedLinkTextView       = (TextView) v.findViewById(R.id.feedLink);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFetchFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchFeedTask().execute((Void) null);
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchFeedTask().execute((Void) null);
            }
        });
        return v;
    }
    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            urlLink = mEditText.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if (!urlLink.startsWith("https://www.scorespro.com/rss2/live-soccer.xml")
                        && !urlLink.startsWith("https://www.scorespro.com/rss2/live-soccer.xml"))
                    urlLink = "https://www.scorespro.com/rss2/live-soccer.xml" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                mFeedTitleTextView.setText      ("Feed Title: "         + mFeedTitle);
                mFeedDescriptionTextView.setText("Feed Description: "   + mFeedDescription);
                mFeedLinkTextView.setText       ("Feed Link: "          + mFeedLink);

                // Fill RecyclerView
                mRecyclerView.setAdapter(new RssFeedListAdapter(mFeedModelList));
            } else {
                Toast.makeText(Blank2Fragment.this.getActivity(),
                        "Enter a valid API feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;

        boolean isItem = false;
        List<RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);


            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }


                if (title != null && link != null && description != null  ) {
                    if (isItem) {
                        RssFeedModel item = new RssFeedModel(title, link, description);
                        items.add(item);
                    } else {
                        mFeedTitle          = title;
                        mFeedLink           = link;
                        mFeedDescription    = description;

                    }

                    title           = null;
                    link            = null;
                    description     = null;


                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

}
