package com.appdav.unknownrunner;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class Dialogs {

    public interface DialogCallback {
        void onClick(View v);
    }

    abstract static class MyDialog extends DialogFragment {

        protected DialogCallback callback;

        private @LayoutRes
        int resId;

        public MyDialog(@LayoutRes int resId, @Nullable DialogCallback callback) {
            this.resId = resId;
            this.callback = callback;
        }

        abstract void findViews(View parent);

        protected void setOnClickListeners(View... views) {
            for (View v : views) {
                v.setOnClickListener(view -> {
                    callback.onClick(view);
                    dismiss();
                });
            }
        }



        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(resId, container, false);
            findViews(v);
            return v;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Activity parent = getActivity();
            if (parent == null) return;
            View decorView = parent.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }

    public static class GameOverDialog extends MyDialog {

        private View restart;
        private View exit;

        public GameOverDialog(@Nullable DialogCallback callback) {
            super(R.layout.dialog_game_over, callback);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setCancelable(false);
        }

        @Override
        void findViews(View parent) {
            restart = parent.findViewById(R.id.tvRestart);
            exit = parent.findViewById(R.id.tvGameOverToMainMenu);
        }

        @Override
        public void onStart() {
            super.onStart();
            setOnClickListeners(restart, exit);
        }


    }


    public static class PauseDialog extends MyDialog {

        private View resume;
        private View exit;

        public PauseDialog(@Nullable DialogCallback callback) {
            super(R.layout.dialog_pause, callback);
        }


        @Override
        void findViews(View parent) {
            resume = parent.findViewById(R.id.tvResume);
            exit = parent.findViewById(R.id.tvPauseToMainMenu);
        }

        @Override
        public void onStart() {
            super.onStart();
            setOnClickListeners(resume, exit);
        }
    }


}
