package com.example.anabada;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//방 생성 클래스
public class RoomRegisterRequest extends StringRequest {
    final static private String URL = "http://gksdldma.cafe24.com/RoomRegister.php";
    private Map<String , String> parameters;
    public RoomRegisterRequest(String roomTitle, String roomPassword, Response.Listener<String> listener )
    {
        super(Method.POST , URL, listener,null); //URL에 POST 방식(요청을 숨겨서)으로 보내라.
        parameters = new HashMap<>(); //각각의 값을 넣을 수 있게 해쉬맵으로
        parameters.put("roomTitle",roomTitle);
        parameters.put("roomPassword",roomPassword);


    }
    @Override
    public Map<String, String> getParams(){
    return parameters;
    }
}
