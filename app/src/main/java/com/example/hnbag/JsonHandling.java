package com.example.hnbag;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class JsonHandling {
    public static void writeToFile(String data, Context context, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream into = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        for (int n; 0 < (n = inputStream.read(buf)); ) {
            into.write(buf, 0, n);
        }
        into.close();
        return new String(into.toByteArray(), "UTF-8"); // Or whatever encoding
    }

    public static InputStream readJsonFromStorage(Context context, String fileName) throws FileNotFoundException {
        FileInputStream fileInputStream = context.openFileInput(fileName);
        return fileInputStream;
    }

    public static String base64Encode(String token) {
        String converted = Base64.encodeToString(token.toString().getBytes(), Base64.DEFAULT);
        return converted;
    }


    public static String base64Decode(String token) {
        String stringFromBase = new String(Base64.decode(token, Base64.DEFAULT));
        return stringFromBase;
    }
}
