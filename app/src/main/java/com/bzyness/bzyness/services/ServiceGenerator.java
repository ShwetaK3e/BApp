package com.bzyness.bzyness.services;

import com.bzyness.bzyness.AppUtils.Constants;
import com.bzyness.bzyness.models.BzynessCategoryDetails;
import com.bzyness.bzyness.models.BzynessTypeDetails;
import com.bzyness.bzyness.models.CreateBzynessServerResponse;
import com.bzyness.bzyness.models.LoginServerResponse;
import com.bzyness.bzyness.models.ProductCatList;
import com.bzyness.bzyness.models.ProductList;
import com.bzyness.bzyness.models.RegistrationServerResponse;
import com.bzyness.bzyness.models.UserDetails;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Pervacio on 6/24/2017.
 */

public class ServiceGenerator {

    private static final String BASE_URL= Constants.BASE_URL;

    private static OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient.Builder();
    private  static OkHttpClient okHttpClient =new OkHttpClient();

    private static Retrofit.Builder builder=new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL);

    private static Retrofit retrofit=builder.build();



    public static <T> T createService( Class<T> serviceClass){

        return createService(serviceClass,null);
    }



    private static void buildHttpInterceptor(final String authToken){
        if(authToken!=null){

            okHttpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original=chain.request();
                    Request.Builder requestBuilder=original.newBuilder().addHeader("Authorization","Bearer "+ authToken);
                    Request request=requestBuilder.build();
                    return  chain.proceed(request);
                }
            });
            okHttpClient =okHttpClientBuilder.build();
        }
    }


    public static <T> T createService(Class<T> serviceClass, final String authToken){
       if(authToken!=null){
           buildHttpInterceptor(authToken);
       }
          T service = retrofit.create(serviceClass);
          okHttpClient=new OkHttpClient();
          return service;
    }


    public interface BzynessClient {

        @POST(Constants.REGISTRATION_URL)
        @Multipart
        Observable<RegistrationServerResponse> registerClient(@PartMap Map<String, RequestBody> newUser);

        @POST(Constants.LOGIN_URL)
        @Headers("Content-Type:application/x-www-form-urlencoded")
        @FormUrlEncoded
        Observable<LoginServerResponse> loginClient(@FieldMap Map<String,String> user);

        @GET(Constants.VALIDATE_LOGIN_URL)
        Observable<UserDetails> validateClient(@Header("Authorization") String token);

        @GET(Constants.BZYNESS_TYPE_URL)
        Observable<BzynessTypeDetails> getBzynessTypes(@Header("Authorization") String token);

        @GET(Constants.BZYNESS_CATEGORY_URL)
        Observable<BzynessCategoryDetails> getBzynessCategories(@Header("Authorization") String token,@Path("type_id") String type_id);

        @POST(Constants.CREATE_BZYNESS_URL)
        @Multipart
        Observable<CreateBzynessServerResponse> createNewBzyness(@Header("Authorization") String token,@PartMap Map<String, RequestBody> newBzyness);

        @POST(Constants.ADD_BZYNESS_TAG_URL)
        @Multipart
        Observable<CreateBzynessServerResponse> addBzynessTag(@Header("Authorization") String token, @PartMap Map<String, RequestBody> newTag);


        @GET(Constants.GET_PRODUCT_CAT_LIST)
        Observable<ProductCatList> getBzynessProductCat(@Path("bzynessId") Integer bzynessId);


        @POST(Constants.ADD_PRODUCT_CAT)
        @Multipart
        Observable<Void> addBzynessProductCat(@Header("Authorization") String token, @Part("bzynessId") Integer bzynessId, @Part("categoryName") String  categoryName , @Part("description") String description, @Part MultipartBody.Part file);

        @GET(Constants.GET_PRODUCT_LIST)
        Observable<ProductList> getBzynessCatProduct(@Path("categoryId") Integer categoryId);
    }


}
