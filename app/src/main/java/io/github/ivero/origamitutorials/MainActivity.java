package io.github.ivero.origamitutorials;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import io.github.ivero.origamitutorials.fragment.AboutFragment;
import io.github.ivero.origamitutorials.fragment.PictureFragment;
import io.github.ivero.origamitutorials.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private VideoFragment videoFragment;
    private PictureFragment pictureFragment;
    private AboutFragment aboutFragment;
    private FrameLayout fl;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_video:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_picture:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_about:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments();
    }

    private void initFragments() {
        fl = (FrameLayout) findViewById(R.id.content);
        VideoFragment videoFragment = new VideoFragment();
        PictureFragment pictureFragment = new PictureFragment();
        AboutFragment aboutFragment = new AboutFragment();
        fragments = new Fragment[]{videoFragment, pictureFragment, aboutFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content, videoFragment)
                .show(videoFragment)
                .commit();
    }

    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    public void switchFrament(int lastIndex, int index) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(fragments[lastIndex]);

        if (!fragments[index].isAdded()) {

            transaction.add(R.id.content, fragments[index]);
        }

        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
