package com.example.anabada;


import android.database.sqlite.SQLiteException;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//방 생성 클래스
public class BoardRegisterRequest extends StringRequest {
    final static private String URL = "http://gksdldma.cafe24.com/BoardRegister.php";
    private Map<String , String> parameters;
    private Map<String,byte[]> para;
    public BoardRegisterRequest(String boardTitle, String boardContents, byte[] boardImageurl, Response.Listener<String> listener )throws Exception
    {
        super(Method.POST , URL, listener,null); //URL에 POST 방식(요청을 숨겨서)으로 보내라.
        parameters = new HashMap<>(); //각각의 값을 넣을 수 있게 해쉬맵으로
        para=new HashMap<>();

        parameters.put("boardTitle",boardTitle);
        parameters.put("boardContents",boardContents);
        //parameters.put("boardImageurl",boardImageurl);
        para.put("boardImageurl",boardImageurl);


    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
