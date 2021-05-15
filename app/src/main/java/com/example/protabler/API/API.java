package com.example.protabler.API;

import com.example.protabler.Dto.LecturerDTO;
import com.example.protabler.Dto.ModuleDTO;
import com.example.protabler.Dto.SessionDTO;
import com.example.protabler.Dto.StudentDTO;
import com.example.protabler.Entities.Login;
import com.example.protabler.Entities.User;
import com.example.protabler.JsonList.BatchList;
import com.example.protabler.JsonList.CourseList;
import com.example.protabler.JsonList.FacultyList;
import com.example.protabler.JsonList.LecturerList;
import com.example.protabler.JsonList.ModuleList;
import com.example.protabler.JsonList.RoomList;
import com.example.protabler.JsonList.SessionList;
import com.example.protabler.JsonList.StudentList;
import com.example.protabler.JsonList.UserList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    //Login user
    @POST("api/user/login")
    Call<String> login(@Body Login login);

    @GET("api/user/getUser/{userId}")
    Call<UserList> getUser(@Path("userId")int userId);

    @GET("api/user/getAllBatches")
    Call<BatchList> getBatches();

    @GET("api/user/getAllCourses")
    Call<CourseList> getCourses();

    @POST("api/user/updateUser")
    Call<String> updateUser(@Body User user);

    @GET("api/student/getTimetable/{userId}")
    Call<SessionList> getTimetable(@Path("userId")int userId);

    @GET("api/lecturer/getTimetable/{userId}")
    Call<SessionList> getTimetableLecturer(@Path("userId")int userId);

    @GET("api/student/getMyModules/{userId}")
    Call<ModuleList> getMyModules(@Path("userId")int userId);

    @GET("api/lecturer/getMyModules/{userId}")
    Call<ModuleList> getLecturerModules(@Path("userId")int userId);

    @GET("api/student/getMyFaculty/{userId}")
    Call<FacultyList> getMyFaculty(@Path("userId")int userId);

    @GET("api/student/getStudent/{userId}")
    Call<StudentList> getStudent(@Path("userId")int userId);

    @POST("api/student/saveStudent")
    Call<String> saveStudent(@Body StudentDTO studentDTO);

    @POST("api/admin/saveSession")
    Call<String> saveSession(@Body SessionDTO sessionDTO);

    @GET("api/admin/getSessions/{batchTitle}")
    Call<SessionList> getBatchSessions(@Path("batchTitle")String batchTitle);

    @GET("api/admin/deleteSession/{sessionId}")
    Call<String> deleteSession(@Path("sessionId")int sessionId);

    @GET("api/admin/getAllRooms")
    Call<RoomList> getAllRooms();

    @GET("api/admin/getAllLecturers")
    Call<LecturerList> getAllLecturers();

    @GET("api/admin/getAllStudents")
    Call<StudentList> getAllStudents();

    @GET("api/admin/getAllModules")
    Call<ModuleList> getAllModules();

    @GET("api/admin/deleteModule/{moduleId}")
    Call<String> deleteModule(@Path("moduleId")int moduleId);

    @GET("api/admin/deleteStudent/{studentId}")
    Call<String> deleteStudent(@Path("studentId")int studentId);

    @GET("api/admin/deleteLecturer/{lecturerId}")
    Call<String> deleteLecturer(@Path("lecturerId")int lecturerId);

    @POST("api/admin/updateModule")
    Call<String> updateModule(@Body ModuleDTO moduleDTO);

    @POST("api/admin/updateStudent")
    Call<String> updateStudent(@Body StudentDTO studentDTO);

    @POST("api/admin/updateLecturer")
    Call<String> updateLecturer(@Body LecturerDTO lecturerDTO);
}
