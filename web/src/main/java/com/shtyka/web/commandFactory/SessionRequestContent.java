package com.shtyka.web.commandFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes = new HashMap<>();
    private HashMap<String, String[]> requestParameters = new HashMap<>();
    private HashMap<String, Object> sessionAttributes = new HashMap<>();
    public HttpSession session;

//    SessionRequestContent(){
//        extractValues();
//    }
    public void extractValues(HttpServletRequest request) {
        String temp;
        this.requestParameters.putAll(request.getParameterMap());
        Enumeration atrNames = request.getAttributeNames();
        while (atrNames.hasMoreElements()) {
            temp = (String)atrNames.nextElement();
            this.requestAttributes.put(temp, request.getAttribute(temp));
        }
        HttpSession currentSession = request.getSession();
        session=currentSession;
        Enumeration sessAtrNames = currentSession.getAttributeNames();
        while (sessAtrNames.hasMoreElements()) {
            temp = (String)sessAtrNames.nextElement();
            this.sessionAttributes.put(temp, currentSession.getAttribute(temp));
        }

    }
    public String[] getParameter(String key) {
        return this.requestParameters.get(key);
    }

    public Object getRequestAttributes(String key) {
        return this.requestAttributes.get(key);
    }



    public void invalidate(){session.invalidate();}
    public void removeAttribute(String value){session.removeAttribute(value);}
    public Object getAttribute(String key) {
        return this.sessionAttributes.get(key);
    }

    public void insertRequestAttributes(String key, Object value) {
        requestAttributes.put(key, value);
    }
    public void setAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
        session.setAttribute(key,value);
    }

  //  возвращаем Request
    public HttpServletRequest insertInRequest(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (sessionAttributes == null) {
            session.invalidate();
//            request.setAttribute(ICommand.MESSAGE, MessageManager.getProperty("message.logout")); добавил в комманде
        } else {
            for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                session.setAttribute(key, value);
            }
        }
        if (requestParameters != null) {
            for (Map.Entry<String, String[]> entry : requestParameters.entrySet()) {
                String key = entry.getKey();
                //String value = entry.getValue();
//                session.setAttribute(key, value);
                //закинуть данные обратно в ответ
            }
        }
        return request;
    }
}
