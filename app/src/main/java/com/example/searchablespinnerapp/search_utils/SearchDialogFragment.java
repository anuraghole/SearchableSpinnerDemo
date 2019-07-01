package com.example.searchablespinnerapp.search_utils;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.searchablespinnerapp.R;

import java.util.ArrayList;

/**
 * Created by Anurag on 28/6/19.
 */
public class SearchDialogFragment<T> extends DialogFragment {

    private String TAG = SearchDialogFragment.class.getSimpleName();
    public static final String KEY_TITLE = "select_item";
    public static final String KEY_TAG = "search_tag";
    public static final String KEY_SEARCH_LIST = "key_search_list";
    public static final String KEY_TYPE_FOR_POPULATE = "fragmentType_or_activityType";
    public static final int TAG_FRAGMENT_TYPE = 1001;
    public static final int TAG_ACTIVITY_TYPE = 1100;
    private int tag;
    private String TITLE;
    private SearchInterface searchInterface;
    private ArrayList<T> searchList;
    private RecyclerView recyclerViewSearch;
    private TextView tvSearchTitle;
    private EditText edtSearchItem;
    private SearchSpinnerAdapter searchSpinnerAdapter;
    private Bundle bundle;
    //toBePopulateType is for from which you are showing the dialog like form an Activity or from Fragment
    private int toBePopulateType=TAG_ACTIVITY_TYPE;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_dialog_fragment, container,
                false);
        bundle = getArguments();

        if (bundle != null) {
            TITLE = bundle.getString(KEY_TITLE, "Select Item");
            tag = bundle.getInt(KEY_TAG);
            toBePopulateType=bundle.getInt(KEY_TYPE_FOR_POPULATE);
            try {
                if (toBePopulateType==TAG_ACTIVITY_TYPE){
                    //if you are showing dialog from Activity
                    searchInterface = (SearchInterface) getActivity();
                }else if (toBePopulateType==TAG_FRAGMENT_TYPE){
                    //if you are showing dialog from Fragment
                    searchInterface = (SearchInterface) getParentFragment();
                }
            } catch (ClassCastException e) {
                throw new ClassCastException("Calling fragment must implement Callback interface");
            }

        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerViewSearch);
        edtSearchItem = (EditText) view.findViewById(R.id.edtSearchItem);
        tvSearchTitle = (TextView) view.findViewById(R.id.tvSearchTitle);
        tvSearchTitle.setText(TITLE);

        searchList = new ArrayList<>();
        searchList = (ArrayList<T>) bundle.getParcelableArrayList(KEY_SEARCH_LIST);


        searchSpinnerAdapter = new SearchSpinnerAdapter(searchList, searchInterface, tag);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSearch.setLayoutManager(layoutManager);
        recyclerViewSearch.setAdapter(searchSpinnerAdapter);
        Log.d(TAG, "onViewCreated: " + searchList);

        edtSearchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchSpinnerAdapter.getFilter().filter(s.toString());
            }

        });

    }

    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
