package co.updn.blingbling.utils;

import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class RetroAPI {

    public interface APICalls {
        @FormUrlEncoded
        @POST("/login")
        Observable<JsonObject> login (
                @Field("username") String username,
                @Field("password") String password
        );

        @POST("/getcategories")
        Observable<JsonObject> getCategories ();

        @POST("/getresources")
        Observable<JsonObject> getResources ();

        @FormUrlEncoded
        @POST("/modifyitem")
        Observable<JsonObject> modifyItem(
                @Field("item_id") String itemId,
                @Field("g_charges") String goldCharges,
                @Field("g_color") String goldColor,
                @Field("g_purity") String goldPurity,
                @Field("d_shape") String diamondShape,
                @Field("d_weight") String diamondWeight,
                @Field("d_color") String diamondColor,
                @Field("d_clarity") String diamondClarity
        );
    }

    public static final APICalls NetworkCalls = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(APICalls.class);
}
