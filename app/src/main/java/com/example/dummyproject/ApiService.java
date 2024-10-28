package com.example.dummyproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("index.php") // Ensure this matches your endpoint
    Call<CertificateResponse> getCertificateById(@Query("cert_id") String certId);
//    @GET("certificates/{id}")
//    Call<CertificateResponse> getCertificateById(@Path("id") String certificateId);

    @GET("certificates")
    Call<List<MyCertificate>> getCertificates();  // Use MyCertificate
}
