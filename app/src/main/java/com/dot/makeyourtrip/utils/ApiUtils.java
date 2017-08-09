package com.dot.makeyourtrip.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.dot.makeyourtrip.model.ErrorModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ApiUtils {
    private static final String TAG = ApiUtils.class.getSimpleName();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Context context;

    public ApiUtils(Context context) {
        this.context = context;
    }

    public String parseErrorAndShow(String tag, Response<?> response){
        try {
            if (response.errorBody() != null) {
                String error = parseError(tag, response);

                ErrorModel errorModel = new Gson().fromJson(error, ErrorModel.class);

                Toast.makeText(context, errorModel.ErrorMessage, Toast.LENGTH_SHORT).show();

                return errorModel.ErrorMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String parseError(String tag, Response<?> response) {
        String tmp = "";
        try {
            if (response.errorBody() != null) {
                tmp = response.errorBody().string();
                Log.e(tag, "" + tmp);
            }
        } catch (IOException e) {
            Log.e(TAG, "" + e.getMessage());
        }

        return tmp;
    }

    public MultipartBody.Part uploadFile(Activity activity, Uri fileUri, String parameterName) {
        File file = new File(getPath(fileUri, activity));

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return MultipartBody.Part.createFormData(parameterName, file.getName(), requestFile);
    }

    private String getPath(Uri uri, Context context) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public boolean checkReadPermission(Context context){
        int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        return true;
    }
}
