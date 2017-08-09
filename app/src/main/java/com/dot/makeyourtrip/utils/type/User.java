package com.dot.makeyourtrip.utils.type;

import com.dot.makeyourtrip.model.UserModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Module
public class User {
    public interface UserRequest {
        @GET("/users")
        Call<List<UserModel>> getUsers();

        @GET("/users/{id}")
        Call<UserModel> getUserByID(@Path("id") String id);

        @GET("/users/me")
        Call<UserModel> getUser();

        @FormUrlEncoded
        @POST("/users")
        Call<UserModel.UserPostModel> createUser(@Field("email") String email,
                                                 @Field("password") String password,
                                                 @Field("name") String name);

        @PUT("/users/me/password")
        Call<UserModel> changePassword(@Query("password") String password);

        @DELETE("/users/delete/{id}")
        Call<UserModel> deleteUser(@Path("id") String ID);

        @DELETE("/users/{id}")
        Call<UserModel> disableUser(@Path("id") String ID);
    }

    @Provides
    public UserRequest provideUserRequest(Retrofit retrofit) {
        return retrofit.create(UserRequest.class);
    }
}
