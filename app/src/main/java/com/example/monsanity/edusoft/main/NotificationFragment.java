package com.example.monsanity.edusoft.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monsanity.edusoft.R;

/**
 * Created by monsanity on 7/21/18.
 */

public class NotificationFragment extends Fragment {

    public static NotificationFragment newInstance() {
        NotificationFragment notificationFragment = new NotificationFragment();
        return notificationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        return view;
    }

}
