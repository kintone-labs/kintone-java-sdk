package userguide;

import com.cybozu.kintone.client.authentication.Auth;
import com.cybozu.kintone.client.connection.Connection;
import com.cybozu.kintone.client.exception.KintoneAPIException;
import com.cybozu.kintone.client.model.comment.*;
import com.cybozu.kintone.client.model.comment.response.AddCommentResponse;
import com.cybozu.kintone.client.model.record.*;
import com.cybozu.kintone.client.model.record.record.response.UpdateRecordsResponse;
import com.cybozu.kintone.client.module.record.Record;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestRecordModel {
    @Test
    public void getCommentRecord() {
        String username = "cybozu";
        String password = "cybozu";
        String kintoneDomain = "https://test1-1.cybozu-dev.com/";

//        String username = "YOUR_USERNAME";
//        String password = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
//        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init Record Module
        Record kintoneRecord = new Record(kintoneConnection);
        Integer appID = 0;  // Input your app id
        Integer recordID = 0;   // Input your record id
        try {
            CommentContent commentContent = new CommentContent();
            ArrayList<CommentMention> mentionList = new ArrayList<>();
            CommentMention mention = new CommentMention();

            mention.setCode("YOUR_USER_MENTION_CODE");
            mention.setType("USER");
            mentionList.add(mention);
            commentContent.setText("TEXT_OF_COMMENT");
            commentContent.setMentions(mentionList);

            AddCommentResponse response = kintoneRecord.addComment(appID, recordID, commentContent);

            int resultId = response.getId();
        } catch (KintoneAPIException e) {
            e.printStackTrace();
        }

    }

    private Integer getLoopTimes(int length, int limitRecord) {
        int loopTimes = length / limitRecord;
        if ((length % limitRecord) > 0) {
            loopTimes++;
        }
        if (length > 0 && length < limitRecord) {
            loopTimes = 1;
        }
        return loopTimes;
    }

    @Test
    public void getFieldModel() {
//        String username = "cybozu";
//        String password = "cybozu";
//        String kintoneDomain = "https://test1-1.cybozu-dev.com/";
//        String query = "$id >=" +  8720 + "and $id <=" + 8750 + "order by $id asc";
        String username = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";

        // Init authenticationAuth
        Auth kintoneAuth = new Auth();
        kintoneAuth.setPasswordAuth(username, password);

        // Init Connection without "guest space ID"
        String kintoneDomain = "YOUR_DOMAIN.COM";
        Connection kintoneConnection = new Connection(kintoneDomain, kintoneAuth);

        // Init Record Module
        Record kintoneRecord = new Record(kintoneConnection);
        Integer appID = 2;  // Input your app id
        Integer recordID = 0;   // Input your record id
        Integer revision = 0;  // Input your revision
        String action = "YOUR_ACTION";
        String assignee = "YOUR_USER_CODE";
        try {

            ArrayList<RecordUpdateStatusItem> recordUpdateStatusItems = new ArrayList<>();

            RecordUpdateStatusItem updateStatusItem = new RecordUpdateStatusItem(action, assignee, recordID, revision);

            recordUpdateStatusItems.add(updateStatusItem);
            UpdateRecordsResponse response = kintoneRecord.updateRecordsStatus(appID, recordUpdateStatusItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
