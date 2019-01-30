package com.ingic.ezhalbatek.technician.retrofit;


import com.ingic.ezhalbatek.technician.entities.AccessoriesDataEnt;
import com.ingic.ezhalbatek.technician.entities.AccessoriesItemEnt;
import com.ingic.ezhalbatek.technician.entities.Accessory;
import com.ingic.ezhalbatek.technician.entities.AdditionalJobsEnt;
import com.ingic.ezhalbatek.technician.entities.AdminEnt;
import com.ingic.ezhalbatek.technician.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.technician.entities.AllJobsEnt;
import com.ingic.ezhalbatek.technician.entities.CreateRoomEnt;
import com.ingic.ezhalbatek.technician.entities.MarkJobDone;
import com.ingic.ezhalbatek.technician.entities.MarkRoomDone;
import com.ingic.ezhalbatek.technician.entities.MarkVisitDone;
import com.ingic.ezhalbatek.technician.entities.MasterReportEnt;
import com.ingic.ezhalbatek.technician.entities.MasterSubscriptionEnt;
import com.ingic.ezhalbatek.technician.entities.MasterTaskEnt;
import com.ingic.ezhalbatek.technician.entities.MasterUserEnt;
import com.ingic.ezhalbatek.technician.entities.NotificationCountEnt;
import com.ingic.ezhalbatek.technician.entities.NotificationsEnt;
import com.ingic.ezhalbatek.technician.entities.ResponseWrapper;
import com.ingic.ezhalbatek.technician.entities.SendSubAdditionalJobs;
import com.ingic.ezhalbatek.technician.entities.SendTaskAdditionalJobs;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.technician.entities.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.technician.entities.UserEnt;
import com.ingic.ezhalbatek.technician.entities.UserPaymentEnt;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebService {


    @FormUrlEncoded
    @POST("login")
    Call<ResponseWrapper<UserEnt>> login(
            @Field("registration_code") String registration_code,
            @Field("role_id") String role_id,
            @Field("device_type") String device_type,
            @Field("device_id") String device_id);


    @GET("request/getsubscriptionpayments")
    Call<ResponseWrapper<ArrayList<SubscriptionPaymentEnt>>> subscriptionPayment(
            @Query("user_id") Integer user_id,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Query("subscription_id") String subscription_id
    );

    @GET("request/getsubscriptionpayments")
    Call<ResponseWrapper<ArrayList<SubscriptionPaymentEnt>>> subscriptionPayment(
            @Query("user_id") Integer user_id,
            @Query("subscription_id") int subscription_id
    );


    @GET("request/gettaskpayments")
    Call<ResponseWrapper<ArrayList<UserPaymentEnt>>> userPayment(
            @Query("user_id") Integer user_id,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Query("service_id") String service_id
    );



    @GET("request/getsubscriptionhistory")
    Call<ResponseWrapper<ArrayList<SubscriptionPaymentEnt>>> subscriptionHistory(
            @Query("user_id") Integer user_id,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Query("subscription_id") String subscription_id
    );



    @GET("request/gettaskhistory")
    Call<ResponseWrapper<ArrayList<UserPaymentEnt>>> userTaskHistory(
            @Query("user_id") Integer user_id,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date,
            @Query("service_id") String service_id
    );



    @GET("request/getitems")
    Call<ResponseWrapper<ArrayList<AccessoriesItemEnt>>> allAccessories();

    @GET("request/getbrands")
    Call<ResponseWrapper<ArrayList<AccessoriesItemEnt>>> getBrands();

    @GET("request/getmodels")
    Call<ResponseWrapper<ArrayList<AccessoriesItemEnt>>> getModels(
            @Query("accessory_id") String accessory_id,
            @Query("type_id") String type_id
    );

    @GET("request/gettypes")
    Call<ResponseWrapper<ArrayList<AccessoriesItemEnt>>> getTypes(
            @Query("accessory_id") String accessory_id);

    @GET("request/getmasterreport")
    Call<ResponseWrapper<ArrayList<MasterUserEnt>>> getMasterReport(
            @Query("name") String name);

    @GET("request/getsubscriptionmaster")
    Call<ResponseWrapper<ArrayList<MasterSubscriptionEnt>>> getMasterReportSubscriber(
            @Query("user_id") String user_id,
            @Query("subscription_id") String subscription_id);

    @GET("request/gettaskmaster")
    Call<ResponseWrapper<ArrayList<MasterTaskEnt>>> getMasterReportTask(
            @Query("user_id") String user_id,
            @Query("service_id") String service_id);


    @GET("request/getmasterreportdetail")
    Call<ResponseWrapper<MasterReportEnt>> getMasterReportDetail(
            @Query("user_id") String user_id,
            @Query("visit_type_id") String visit_type_id);


    @GET("request/getjobdates")
    Call<ResponseWrapper<ArrayList<AllJobsEnt>>> getAllJobs(
            @Query("user_id") String user_id,
            @Query("start_date") String start_date,
            @Query("end_date") String end_date);

    @GET("request/newrequestjobs")
    Call<ResponseWrapper<ArrayList<UserPaymentEnt>>> getNewrRquestJobs(
            @Query("user_id") String user_id,
            @Query("date") String date);


    @GET("request/subscriptionjobs")
    Call<ResponseWrapper<ArrayList<SubscriptionPaymentEnt>>> getNewSubscriptionJobs(
            @Query("user_id") String user_id,
            @Query("date") String date);

    @FormUrlEncoded
    @POST("request/createroom")
    Call<ResponseWrapper<CreateRoomEnt>> createRoom(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("user_subs_visit_id") String user_subs_visit_id,
            @Field("request_id") String request_id
    );



    @FormUrlEncoded
    @POST("request/addroomaccessory")
    Call<ResponseWrapper<AccessoriesDataEnt>> addRoomAccessories(
            @Field("accessory_id") String accessory_id,
            @Field("room_id") String room_id,
            @Field("quantity") String quantity,
            @Field("subscription_id") String subscription_id,
            @Field("visit_id") String visit_id,
            @Field("brand_id") String brand_id,
            @Field("item_model_id") String item_model_id,
            @Field("type_id") String type_id
    );

    @GET("request/getvisitdetails")
    Call<ResponseWrapper<SubscriptionPaymentEnt>> getRoomDetail(
            @Query("visit_id") String visit_id);


    @POST("request/markroomdone")
    Call<ResponseWrapper> markRoomDone(
            @Body MarkRoomDone body);

    @POST("request/markvisitdone")
    Call<ResponseWrapper> markVisitDone(
            @Body MarkVisitDone body);

    @POST("request/markjobdone")
    Call<ResponseWrapper> markJobDone(
            @Body MarkJobDone body);

    @FormUrlEncoded
    @POST("user/changepassword")
    Call<ResponseWrapper> changePassword(
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("user/logout")
    Call<ResponseWrapper> logout(
            @Field("user_id") String user_id,
            @Field("device_id") String device_id
    );

    @GET("user/getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationsEnt>>> getNotifications(
            @Query("user_id") String user_id);


    @FormUrlEncoded
    @POST("user/togglenotification")
    Call<ResponseWrapper> toggleNotification(
            @Field("user_id") String user_id,
            @Field("is_notification") String is_notification
    );

    @FormUrlEncoded
    @POST("request/deleteroom")
    Call<ResponseWrapper> deleteRoom(
            @Field("user_id") int user_id,
            @Field("room_id") int room_id
    );



    @GET("service/allservice")
    Call<ResponseWrapper<ArrayList<AllCategoriesEnt>>> getAllCategories(
    );

    @GET("subscription/subscriptioncategories")
    Call<ResponseWrapper<ArrayList<SubscriptionPackagesEnt>>> getAllPackages(
    );

    @GET("request/getadditionaljobs")
    Call<ResponseWrapper<ArrayList<AdditionalJobsEnt>>> getAdditionalJobs(
            @Query("user_id") String user_id);

    @GET("request/getroomaccessories")
    Call<ResponseWrapper<ArrayList<Accessory>>> getRoomAccessories(
            @Query("room_id") String user_id);

    @GET("request/getrequestjob")
    Call<ResponseWrapper<UserPaymentEnt>> getRequestData(
            @Query("user_id") int user_id,
            @Query("service_id") String service_id);

    @POST("request/sendadditionaljobs")
    Call<ResponseWrapper> sendSubAdditionalJobs(
            @Body SendSubAdditionalJobs body);

    @POST("request/sendadditionaljobs")
    Call<ResponseWrapper> sendTaskAdditionalJobs(
            @Body SendTaskAdditionalJobs body);


    @GET("user/getadminuser")
    Call<ResponseWrapper<AdminEnt>> getAdminDetail();

    @FormUrlEncoded
    @POST("user/deletenotification")
    Call<ResponseWrapper> deleteNotification(
            @Field("user_id") int user_id,
            @Field("notification_ids") String notification_ids
    );
    @FormUrlEncoded
    @POST("user/deletenotification")
    Call<ResponseWrapper> deleteAllNotification(
            @Field("user_id") int user_id
    );

    @GET("user/getunreadnotificationscount")
    Call<ResponseWrapper<NotificationCountEnt>> notificationCount(
            @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/marknotificationread")
    Call<ResponseWrapper> markUnRead(
            @Field("user_id") int user_id,
            @Field("notification_id") int notification_id
    );


}