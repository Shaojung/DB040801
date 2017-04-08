package com.example.teacher.db040801;

import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getAbsolutePath() + File.separator + "students.sqlite", null);
        // Cursor c = db.rawQuery("select * from phone", new String[] {});
        Cursor c = db.query("phone", new String[] {"ID", "Name", "Tel", "Addr"}, null, null, null, null, null);
        if (c.moveToFirst())
        {
            do {
                String n = c.getString(1);
                Log.d("MYDB", n);
            }while(c.moveToNext());
        }
    }

    private void copyDatabase() throws IOException {
        // Path to the empty database.
        String outFilename = getFilesDir().getAbsolutePath() + File.separator + "students.sqlite";

        // Open the empty database as the output stream.
        OutputStream outputDatabase = new FileOutputStream(outFilename);
        // Transfer bytes from the input file to the output file.
        byte[] buffer = new byte[1024];
        InputStream is = this.getAssets().open("students.sqlite");
        int length;
        while ((length = is.read(buffer)) > 0) {
            outputDatabase.write(buffer, 0, length);
        }
        is.close();

        outputDatabase.flush();
        outputDatabase.close();

    }
}
