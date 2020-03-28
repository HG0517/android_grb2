package com.jgkj.grb.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * Created by jugekeji on 2018/7/18.
 */

public class LocationManagerUtil {
    public static final int REQUEST_CODE_LOCATION_SETTING = 10009;

    /**
     * 手机自身的定位开关是否开启
     *
     * @param context Context
     * @return 是否开启
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 手机自身的定位开关未开启，询问是否开启
     *
     * @param activity Activity
     * @param message  String
     * @return AlertDialog
     */
    public static AlertDialog locServiceUnenable(final Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("开启", (dialog, which) -> {
            // 启动定位 Activity
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(intent, REQUEST_CODE_LOCATION_SETTING);
        });

        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        return builder.create();
    }

    /**
     * 获取位置：Location 获取经纬度
     *
     * @param activity Activity
     * @return Location
     */
    public static Location getLocation(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 没有位置权限，进行权限的申请
            Logger.i("TAG_LocationManagerUtil", "getLocation: 没有权限");
            return null;

        } else {
            // 获取LocationManager的实例对象
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            // 获取支持的provider列表
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            // 遍历provider列表
            for (String provider : providers) {
                Logger.i("TAG_LocationManagerUtil", "getLocation: " + provider);
                // 通过getLastKnowLocation方法来获取
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                // 准确度
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    bestLocation = l;
                }
            }
            return bestLocation;
        }
    }

    public static Location getLocationByGPS(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 没有位置权限，进行权限的申请
            Logger.i("TAG_LocationManagerUtil", "getLocation:GPS 没有权限");
            return null;

        } else {
            // 获取LocationManager的实例对象
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    }

    public static Location getLocationByNetwork(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 没有位置权限，进行权限的申请
            Logger.i("TAG_LocationManagerUtil", "getLocation:Network 没有权限");
            return null;

        } else {
            // 获取LocationManager的实例对象
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public static Location getLocationPassive(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: 没有位置权限，进行权限的申请
            Logger.i("TAG_LocationManagerUtil", "getLocation:Passive 没有权限");
            return null;

        } else {
            // 获取LocationManager的实例对象
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            return locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
    }

    public static Address getAddressByGeocoder(Activity activity, Location location) {
        if (null == location) {
            return null;
        }

        Logger.i("TAG_LocationManagerUtil", "getAddressByGeocoder: " + location.getLatitude() + ", " + location.getLongitude());
        // 判断网络
        if (NetworkUtils.getNetworkType() != NetworkUtils.NetworkType.NETWORK_NO) {
            try {
                Geocoder geocoder = new Geocoder(activity);
                List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (null == fromLocation || fromLocation.size() <= 0)
                    return null;

                return fromLocation.get(0);
            } catch (IOException e) {
                return null;
            }
        } else {
            Toast.makeText(activity, "没有网络", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
