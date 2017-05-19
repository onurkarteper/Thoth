package com.devfie.thoth.selectcategories;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.devfie.thoth.R;
import com.devfie.thoth.manager.LocalDataManager;
import com.devfie.thoth.model.Category;
import com.devfie.thoth.model.response.AskQuestionResponse;
import com.devfie.thoth.model.response.CategoryResponse;
import com.devfie.thoth.network.NetworkRequests;
import com.devfie.thoth.network.NetworkResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac-onur on 8.04.2017.
 */

public class CategoriesPresenter implements CategoriesContract.Presenter {
    private final CategoriesContract.View categoriesView;


    public CategoriesPresenter(CategoriesContract.View categoriesView) {
        this.categoriesView = categoriesView;
        categoriesView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void getCategories(final Context context) {
        NetworkRequests.with(context).getCategories(LocalDataManager.getInstance().getToken(), new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                CategoryResponse categoryResponse = (CategoryResponse) obj;
                categoriesView.categoryListLoaded(categoryResponse.getCategories());
            }

            @Override
            public void onError(String errorMsg) {
                categoriesView.categoryListCantLoaded(errorMsg);
            }

            @Override
            public void onServerError() {
                categoriesView.categoryListCantLoaded(context.getString(R.string.error_category_list_cant_load));
            }
        });
    }

    @Override
    public void uploadQuestion(final Context context, String imageUrl, String title, String description, final List<Category> categories, Float ratio) {
        List<Category> checkedList = getCheckedList(categories);
        if (checkedList.size() == 0) {
            categoriesView.categoryUnselected();
            return;
        }

        NetworkRequests.with(context).newQuestion(LocalDataManager.getInstance().getToken(), checkedList, imageUrl, title, description, ratio, new NetworkResponse() {
            @Override
            public void onSuccess(Object obj) {
                AskQuestionResponse response = (AskQuestionResponse) obj;
                categoriesView.questionUploaded(response.getQuestion());
            }

            @Override
            public void onError(String errorMsg) {
                categoriesView.questionCantUploaded(errorMsg);
            }

            @Override
            public void onServerError() {
                categoriesView.questionCantUploaded(context.getString(R.string.error_question_upload));

            }
        });

    }


    @Override
    public void uploadImage(String path) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                categoriesView.imageUploadFailed();
                // Handle unsuccessful uploads
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                categoriesView.uploadImageProgressChange(taskSnapshot.getTotalByteCount(), taskSnapshot.getBytesTransferred());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                categoriesView.imageUploaded(taskSnapshot.getDownloadUrl().toString());
            }
        });
    }

    public List<Category> getCheckedList(List<Category> categories) {
        List<Category> checkedList = new ArrayList<>();
        for (Category category : categories) {
            if (category.getCheck()) checkedList.add(category);
        }
        return checkedList;
    }
}
