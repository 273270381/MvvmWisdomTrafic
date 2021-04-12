package com.suchness.mvvmwisdomtrafic.ui.home;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.Navigation;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.suchness.mvvmwisdomtrafic.BR;
import com.suchness.mvvmwisdomtrafic.R;
import com.suchness.mvvmwisdomtrafic.databinding.ActivityHomeBinding;
import com.suchness.mvvmwisdomtrafic.ui.alarm.AlarmFragment;
import com.suchness.mvvmwisdomtrafic.ui.file.FileFragment;
import com.suchness.mvvmwisdomtrafic.ui.monitor.MonitorFragment;
import com.suchness.mvvmwisdomtrafic.view.FixFragmentNavigator;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

public class HomeActivity extends BaseActivity<ActivityHomeBinding,HomeViewModel> {
    public NavController navController;
    private long mBackPressedTime;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        navController = Navigation.findNavController(this,R.id.nav_main_fragment);
        binding.btn.setOnNavigationItemSelectedListener(item -> {
            navController.navigate(item.getItemId());
            return true;
        });


//        Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.nav_main_fragment);
//        navController = NavHostFragment.findNavController(fragmentById);
//        NavigatorProvider provider = navController.getNavigatorProvider();
//        FixFragmentNavigator fixFragmentNavigator = new FixFragmentNavigator(this,fragmentById.getChildFragmentManager(),fragmentById.getId());
//        provider.addNavigator(fixFragmentNavigator);
//        NavGraph navGraph = initNavGraph(provider,fixFragmentNavigator);
//        navController.setGraph(navGraph);
//        binding.btn.setOnNavigationItemSelectedListener(item -> {
//            navController.navigate(item.getItemId());
//            return true;
//        });
    }

//    private NavGraph initNavGraph(NavigatorProvider provider, FixFragmentNavigator fragmentNavigator) {
//        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));
//        //用自定义的导航器来创建目的地
//        FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
//        destination.setId(R.id.alarmFragment);
//        destination.setClassName(AlarmFragment.class.getCanonicalName());
//        navGraph.addDestination(destination);
//
//        FragmentNavigator.Destination destination1 = fragmentNavigator.createDestination();
//        destination1.setId(R.id.monitorFragment);
//        destination1.setClassName(MonitorFragment.class.getCanonicalName());
//        navGraph.addDestination(destination1);
//
//        FragmentNavigator.Destination destination2 = fragmentNavigator.createDestination();
//        destination2.setId(R.id.fileFragment);
//        destination2.setClassName(FileFragment.class.getCanonicalName());
//        navGraph.addDestination(destination2);
//
//        navGraph.setStartDestination(destination.getId());
//        return navGraph;
//    }

    @Override
    public void onBackPressed() {
        NavDestination destination = navController.getCurrentDestination();
        if (destination.getId() == R.id.fileFragment ||
            destination.getId() == R.id.alarmFragment ||
            destination.getId() == R.id.monitorFragment){
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)){
                finish();
            }else{
                mBackPressedTime = curTime;
                Toast.makeText(this, R.string.tip_double_click_exit, Toast.LENGTH_LONG).show();
            }
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_main_fragment);
        if (fragment != null){
            return NavHostFragment.findNavController(fragment).navigateUp();
        }else{
            return super.onSupportNavigateUp();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        KLog.d("hjf","activityonSupportNavigateUp");
        NavController navController = Navigation.findNavController(this,R.id.nav_main_fragment);
        return NavigationUI.onNavDestinationSelected(item,navController) || super.onOptionsItemSelected(item);
    }
}