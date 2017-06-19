package com.devfie.thoth.network;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.devfie.thoth.data.Constants;
import com.devfie.thoth.data.Md5Helper;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.response.AnswerVoteResponse;
import com.devfie.thoth.model.response.AskQuestionResponse;
import com.devfie.thoth.model.response.BaseResponse;
import com.devfie.thoth.model.response.CategoryResponse;
import com.devfie.thoth.model.response.LoginResponse;
import com.devfie.thoth.model.response.MessageResponse;
import com.devfie.thoth.model.response.QuestionDetailResponse;
import com.devfie.thoth.model.response.QuestionResponse;
import com.devfie.thoth.model.response.UserInfoResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Created by Onur Karteper on 4/2/2017.
 * All network request in the app.
 */

public class NetworkRequests {
    Context mContext;

    public NetworkRequests(Context mContext) {
        this.mContext = mContext;
    }

    public static NetworkRequests with(Context mContext) {
        return new NetworkRequests(mContext);
    }

    public static NetworkRequests with(Fragment fragment) {
        return new NetworkRequests(fragment.getContext());
    }

    public static NetworkRequests with(Activity activity) {
        return new NetworkRequests(activity);
    }


    @SuppressWarnings("unchecked")
    public void login(String email, String password, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(LoginResponse.class);
        networkManager.setNetworkMethod(Constants.API_LOGIN);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_EMAIL, email);
        jsonObject.addProperty(Constants.KEY_PASSWORD, Md5Helper.md5(password));
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void checkEmail(String email, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(BaseResponse.class);
        networkManager.setNetworkMethod(Constants.API_CHECK_EMAIL);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_EMAIL, email);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void checkUsername(String email, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(BaseResponse.class);
        networkManager.setNetworkMethod(Constants.API_CHECK_USERNAME);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_USERNAME, email);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }


    @SuppressWarnings("unchecked")
    public void register(String email, String username, String password, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        networkManager.setTypeToken(LoginResponse.class);
        networkManager.setNetworkMethod(Constants.API_REGISTER);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_EMAIL, email);
        jsonObject.addProperty(Constants.KEY_PASSWORD, Md5Helper.md5(password));
        jsonObject.addProperty(Constants.KEY_USERNAME, username);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void newQuestion(String appToken, List<Category> cats, String image, String title, String description, Float ratio, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add(Constants.KEY_CATEGORIES, new Gson().toJsonTree(cats));
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_IMAGE, image);
        jsonObject.addProperty(Constants.KEY_DESCRIPTION, description);
        jsonObject.addProperty(Constants.KEY_TITLE, title);
        jsonObject.addProperty(Constants.KEY_RATIO, ratio);
        networkManager.setNetworkMethod(Constants.API_NEW_QUESTION);
        networkManager.setTypeToken(AskQuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void getCategories(String appToken, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        networkManager.setNetworkMethod(Constants.API_CATEGORY_LIST);
        networkManager.setTypeToken(CategoryResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void getQuestions(String appToken, int page, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_PAGE, page);
        networkManager.setNetworkMethod(Constants.API_QUESTION_LIST);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void followQuestion(String appToken, String questionId, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_ID, questionId);
        networkManager.setNetworkMethod(Constants.API_FOLLOW_QUESTION);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void deleteQuestion(String appToken, String questionId, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_ID, questionId);
        networkManager.setNetworkMethod(Constants.API_DELETE_QUESTION);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void sendAsnwer(String appToken, String questionId, String image, String description, Float ratio, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_QUESTION_ID, questionId);
        jsonObject.addProperty(Constants.KEY_IMAGE, image);
        jsonObject.addProperty(Constants.KEY_DESCRIPTION, description);
        jsonObject.addProperty(Constants.KEY_RATIO, ratio);
        networkManager.setNetworkMethod(Constants.API_SEND_ANSWER);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void sendVote(String appToken, String answerId, Boolean voteType, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_ANSWER_ID, answerId);
        jsonObject.addProperty(Constants.KEY_ANSWER_VOTE_TYPE, voteType);
        networkManager.setNetworkMethod(Constants.API_SEND_VOTE);
        networkManager.setTypeToken(AnswerVoteResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void getQuestion(String appToken, String quetionId, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_ID, quetionId);
        networkManager.setNetworkMethod(Constants.API_GET_QUESTION);
        networkManager.setTypeToken(QuestionDetailResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void reportQuestion(String token, String id, String cause, String ownerId, Boolean isBlock, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, token);
        jsonObject.addProperty(Constants.KEY_ID, id);
        jsonObject.addProperty(Constants.KEY_CAUSE, cause);
        jsonObject.addProperty(Constants.KEY_IS_BLOCK, isBlock);
        jsonObject.addProperty(Constants.KEY_OWNER_ID, ownerId);
        networkManager.setNetworkMethod(Constants.API_REPORT_QUESTION);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void reportAnswer(String token, String id, String cause, String ownerId, Boolean isBlock, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, token);
        jsonObject.addProperty(Constants.KEY_ID, id);
        jsonObject.addProperty(Constants.KEY_CAUSE, cause);
        jsonObject.addProperty(Constants.KEY_IS_BLOCK, isBlock);
        jsonObject.addProperty(Constants.KEY_OWNER_ID, ownerId);
        networkManager.setNetworkMethod(Constants.API_REPORT_ANSWER);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void deleteAnswer(String appToken, String answerId, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, appToken);
        jsonObject.addProperty(Constants.KEY_ID, answerId);
        networkManager.setNetworkMethod(Constants.API_DELETE_ANSWER);
        networkManager.setTypeToken(QuestionResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void getMyInfo(String token, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, token);
        networkManager.setNetworkMethod(Constants.API_GET_INFO);
        networkManager.setTypeToken(UserInfoResponse.class);
        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }

    @SuppressWarnings("unchecked")
    public void getMessasgeList(String token, List<String> users, String lastMessageDate, Integer page, NetworkResponse response) {
        NetworkManager networkManager = NetworkManager.with(mContext);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constants.KEY_TOKEN, token);
        jsonObject.add(Constants.KEY_PARTICIPANTS, new Gson().toJsonTree(users));
        jsonObject.addProperty(Constants.KEY_PAGE, page);
        networkManager.setNetworkMethod(Constants.API_MESSAGE_LIST);
        networkManager.setTypeToken(MessageResponse.class);

        networkManager.setJsonBody(jsonObject);
        networkManager.makeRequest(response);
    }


}
