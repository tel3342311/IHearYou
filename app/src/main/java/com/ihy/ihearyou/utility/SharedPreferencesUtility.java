package com.ihy.ihearyou.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SharedPreferencesUtility {

    static final String TAG = SharedPreferencesUtility.class.getSimpleName();
    static final String Base64_Data = "base64";

    static public void writeSharedPreference(Context context, String key, Object object)
    {
        SharedPreferences sp = context.getSharedPreferences(Base64_Data, Context.MODE_PRIVATE);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String base64String = new String(Base64.encodeBase64(baos.toByteArray()));
            sp.edit().putString(key, base64String).commit();
        }
        catch (Exception e)
        {
            Log.i(TAG, e.getMessage());
        }
    }

    static public Object readSharedPreference(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(Base64_Data, Context.MODE_PRIVATE);
        String base64String = sp.getString(key, "");
        if(base64String.equals(""))
            return null;

        byte[] base64Array = Base64.decodeBase64(base64String.getBytes());
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Array);
        Object result = null;
        try
        {
            ObjectInputStream ois = new ObjectInputStream(bais);
            try
            {
                result = ois.readObject();
            }
            catch(Exception e)
            {
                Log.i(TAG, e.getMessage());
            }
        }
        catch(Exception e)
        {
            Log.i(TAG, e.getMessage());
        }
        return result;
    }

    static public void removeSharedPreference(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(Base64_Data, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    static public void cleanSharedPreference(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(Base64_Data, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
