package com.example.searchablespinnerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.searchablespinnerapp.search_utils.SearchDialogFragment;
import com.example.searchablespinnerapp.search_utils.SearchInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchInterface {

    private static final int SAMPLE_TAG = 7;
    private ArrayList<VendorsModel> vendorsModelArrayList = new ArrayList<>();
    private SearchDialogFragment dialogFragments;
    private TextView tvSpinnerChooseVendor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvSpinnerChooseVendor = findViewById(R.id.tvSpinnerChooseVendor);

        tvSpinnerChooseVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragments = new SearchDialogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(SearchDialogFragment.KEY_TITLE, "Search Vendor");
                bundle1.putParcelableArrayList(SearchDialogFragment.KEY_SEARCH_LIST, getVendorsModelArrayList());
                bundle1.putInt(SearchDialogFragment.KEY_TAG, SAMPLE_TAG);

                // put KEY_TYPE_FOR_POPULATE is SearchDialogFragment.TAG_ACTIVITY_TYPE
                //for showing Dilog from Activity
                bundle1.putInt(SearchDialogFragment.KEY_TYPE_FOR_POPULATE, SearchDialogFragment.TAG_ACTIVITY_TYPE);

                //for show DialogFragment on Fragment
                //bundle1.putInt(SearchDialogFragment.KEY_TYPE_FOR_POPULATE, SearchDialogFragment.TAG_FRAGMENT_TYPE);
                //dialogFragments.show(AnyFragment.this.getChildFragmentManager(), "Connections");

                dialogFragments.setArguments(bundle1);
                dialogFragments.show(getSupportFragmentManager(), SearchDialogFragment.KEY_TAG);
            }
        });
    }

    @Override
    public void setSearchClickListener(int position, int tag) {
        if (tag == SAMPLE_TAG) {
            VendorsModel vendorsModel = vendorsModelArrayList.get(position);
            Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show();
            tvSpinnerChooseVendor.setText(vendorsModel.getVendorName());
                /*Intent intent = new Intent(ChooseVendorActivity.this, GlobalInscanActivity.class);
                intent.putExtra("VendorDetails", vendorsModel);
                startActivity(intent);*/
            dialogFragments.dismiss();
        }
    }

    public ArrayList<VendorsModel> getVendorsModelArrayList() {
        if (vendorsModelArrayList == null) {
            vendorsModelArrayList = new ArrayList<>();
        } else {
            vendorsModelArrayList.clear();
            for (int i = 0; i < 30; i++) {
                VendorsModel vendorsModel = new VendorsModel();

                vendorsModel.setVendorName("Vendor " + i);
                vendorsModel.setVendorAddress("Vendor Address " + i);
                vendorsModelArrayList.add(vendorsModel);
            }
        }
        return vendorsModelArrayList;
    }
}
