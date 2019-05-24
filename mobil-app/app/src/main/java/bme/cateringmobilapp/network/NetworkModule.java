package bme.cateringmobilapp.network;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public Retrofit.Builder builder() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());
    }

    @Provides
    @Singleton
    public CateringApi cateringApi(Retrofit.Builder builder) {
        return builder.baseUrl(NetworkConfig.ENDPOINT_ADDRESS)
                .build().create(CateringApi.class);
    }

}
