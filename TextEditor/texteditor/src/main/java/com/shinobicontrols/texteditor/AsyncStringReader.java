/*
Copyright 2014 Scott Logic Ltd

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.shinobicontrols.texteditor;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AsyncStringReader extends AsyncTask<Uri, Void, String> {

    private AsyncStringReaderCompletionHandler mCompletionHandler;
    private ContentResolver mContentResolver;

    public AsyncStringReader(ContentResolver contentResolver,
                             AsyncStringReaderCompletionHandler completionHandler) {
        mContentResolver   = contentResolver;
        mCompletionHandler = completionHandler;
    }

    @Override
    protected String doInBackground(Uri... params) {
        String resultString = "";
        try{
            InputStream inputStream = mContentResolver.openInputStream(params[0]);
            if(inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                inputStream.close();
                resultString = stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    @Override
    protected void onPostExecute(String s) {
        mCompletionHandler.setText(s);
    }
}
