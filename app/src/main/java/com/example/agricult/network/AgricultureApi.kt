package com.example.agricult.network

import android.app.DatePickerDialog
import android.graphics.Bitmap
import com.example.agricult.models.categories.CategoriesModel
import com.example.agricult.models.category.CategoryModel
import com.example.agricult.models.contacts.ContactModel
import com.example.agricult.models.loginResult.AuthResult
import com.example.agricult.models.profileShowResult.ProfileShowResult
import com.example.agricult.models.requests.LoginUserModel
import com.example.agricult.models.requests.RegistrationUser
import com.example.agricult.models.search.SearchModel
import com.example.agricult.models.updateProfileUser.UpdateProfileUserModel
import com.example.agricult.viewmodel.SearchViewModel
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface AgricultureApi {

    @POST("api/auth/login")
    @Headers(
        "Accept:application/json", "Content-Type:application/json"
    )
    fun login(
        @Body authenticationUser: LoginUserModel
    ): Call<AuthResult>



    @POST("api/auth/register")
    @Headers(
        "Accept:application/json", "Content-Type:application/json"
    )
    fun register(
        @Body registrationUser: RegistrationUser
    ): Call<AuthResult>


    @GET("/api/profile/show")
    fun getProfileShow(
        @Header(value = "Authorization") token: String
    ): Call<ProfileShowResult>


    @GET("/api/advertisements/categories")
    fun getCategories(
        @Header(value = "Authorization") token: String
    ): Call<List<CategoriesModel>>


    @GET("/api/advertisements/")
    fun getCategory(
        @Header(value = "Authorization") token: String,
        @Query("category_id") categoryId: Int,
        @Query("order_by") orderBy: String? = "desc",
        @Query("price_from") priceFrom: Int? = 0,
        @Query("price_to") priceTo: Int? = 1000000,
        @Query("page") page: Int? = 1
    ): Call<CategoryModel>


    @GET("api/advertisements/search")
    fun getSearchAnnouncement(
        @Header(value = "Authorization") token: String,
        @Query("query") query: String,
        @Query("order_by") orderBy: String = "desk",
        @Query("price_from") priceFrom: Int,
        @Query("price_to") priceTo: Int,
        @Query("page") page: Int = 1
    ): Call<SearchModel>


    @GET("api/profile/favorites")
    fun getFavouritesAnnouncement(
        @Header(value = "Authorization") token: String
    ): Call<CategoryModel>


    @FormUrlEncoded
    @POST("api/profile/favorites")
    fun favoritesStore(
        @Header(value = "Authorization") token: String,
        @Field("favorite_id") favorite_id: Int
    ): Call<String>


    @DELETE("api/profile/favorites/{favorite_id}")
    fun deleteFavoritesStore(
        @Header(value = "Authorization") token: String,
        @Path("favorite_id") favoriteId: Int
    ): Call<String>


    @GET("api/profile/center")
    fun getContact(
        @Header(value = "Authorization") token: String,
    ): Call<ContactModel>


    @POST("api/profile/update?_method=PUT")
    fun updateUserProfileData(
        @Header(value = "Authorization") token: String,
        @Body updateProfileUserModel: UpdateProfileUserModel
    ): Call<String>


}