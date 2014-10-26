package com.example.lexusqueue;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.io.File;


public class main extends Activity {
    NfcAdapter mNfcAdapter;
    private FileUriCallback mFileUriCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            return;
        }
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        /*
         * Instantiate a new FileUriCallback to handle requests for
         * URIs
         */
        //mFileUriCallback = new FileUriCallback();
        // Set the dynamic callback for URI requests.
        //mNfcAdapter.setBeamPushUrisCallback(mFileUriCallback,this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Uri[] mFileUris = new Uri[1];
    /**
     * Callback that Android Beam file transfer calls to get
     * files to share
     */
    private class FileUriCallback implements NfcAdapter.CreateBeamUrisCallback {
        public FileUriCallback() {
        }
        @Override
        public Uri[] createBeamUris(NfcEvent event) {

            return mFileUris;
        }
    }

    public void choose(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        Intent i = Intent.createChooser(intent, "File");
        startActivityForResult(i, 1);



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri file = data.getData();
            \Uri[] mFileUris = new Uri[1];
            Log.i("hello", file.toString());
            //File myFile = new File(file.toString());
            //myFile.setReadable(true, false);
            //file = Uri.fromFile(msyFile);
            if (file != null) {
            mFileUris[0] = file;
            } else {
                Log.e("My Activity", "No File URI available for file.");
            }
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
            nfcAdapter.setBeamPushUris(mFileUris,this);
            //if (nfcAdapter == null) return;  // NFC not available on this device
/*
            mFileUriCallback = new FileUriCallback();

            mNfcAdapter.setBeamPushUrisCallback(mFileUriCallback, this);
  */
        }
    }
/*
    Intent mIntent;
    File mParentPath;
    private void handleViewIntent() {
        // Get the Intent action
        mIntent = getIntent();
        String action = mIntent.getAction();
        //
        // For ACTION_VIEW, the Activity is being asked to display data.
        // Get the URI.
        //
        if (TextUtils.equals(action, Intent.ACTION_VIEW)) {
            // Get the URI from the Intent
            Uri beamUri = mIntent.getData();
            //
            // Test for the type of URI, by getting its scheme value
            //
            if (TextUtils.equals(beamUri.getScheme(), "file")) {
                mParentPath = handleFileUri(beamUri);
            } else if (TextUtils.equals(
                    beamUri.getScheme(), "content")) {
                mParentPath = handleContentUri(beamUri);
            }
        }
    }

    public File handleFileUri(Uri beamUri) {
        // Get the path part of the URI
        String fileName = beamUri.getPath();
        // Create a File object for this filename
        File copiedFile = new File(fileName);
        // Get a string containing the file's parent directory
        return new File(copiedFile.getParent());
    }
    public File handleContentUri(Uri beamUri) {
        // Position of the filename in the query Cursor
        int filenameIndex;
        // File object for the filename
        File copiedFile;
        // The filename stored in MediaStore
        String fileName;
        // Test the authority of the URI
        if (!TextUtils.equals(beamUri.getAuthority(), MediaStore.AUTHORITY)) {
            //
            // Handle content URIs for other content providers
            // For a MediaStore content URI
        } else {
            // Get the column that contains the file name
            String[] projection = { MediaStore.MediaColumns.DATA };
            Cursor pathCursor =
                    getContentResolver().query(beamUri, projection,
                            null, null, null);
            // Check for a valid cursor
            if (pathCursor != null &&
                    pathCursor.moveToFirst()) {
                // Get the column index in the Cursor
                filenameIndex = pathCursor.getColumnIndex(
                        MediaStore.MediaColumns.DATA);
                // Get the full file name including path
                fileName = pathCursor.getString(filenameIndex);
                // Create a File object for the filename
                copiedFile = new File(fileName);
                // Return the parent directory of the file
                return new File(copiedFile.getParent());
            } else {
                // The query didn't work; return null
                return null;
            }
        }
        return null;
    }
*/
}
