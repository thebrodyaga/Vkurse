package com.example.win10.vkurse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        VKRequest request = VKApi.wall().get(VKParameters.from("owner_id", "-151864621", VKApiConst.COUNT, 2, VKApiConst.EXTENDED, 1/*, VKApiConst.OFFSET, 3*/));
        System.out.println(VKAccessToken.ACCESS_TOKEN.toString());
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Log.d("MyLog", response.json.toString());
                VKList<VKApiPost> posts = (VKList<VKApiPost>) response.parsedModel;
                VKApiPost post = posts.get(1);
                VKAttachments attachments = post.attachments;
                for (int i = 0; i < attachments.size(); i++) {
                    Log.d("Post:", attachments.get(i).getType());
                }
            }

            @Override
            public void onError(VKError error) {
                System.out.println(error.errorMessage);
                System.out.println(error.errorCode);
            }
        });
    }
}
