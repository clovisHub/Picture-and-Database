package com.example.w3d5ex01.util;

import com.example.w3d5ex01.model.Id;
import com.example.w3d5ex01.model.Location;
import com.example.w3d5ex01.model.Login;
import com.example.w3d5ex01.model.Name;
import com.example.w3d5ex01.model.Picture;
import com.example.w3d5ex01.model.Result;

import org.json.JSONException;
import org.json.JSONObject;

public final class RandomParser {

    private RandomParser() {
        // prevent unwanted instantiation of this class.
    }

    public static Result parseResult(JSONObject jsonResult) throws JSONException {
        Result result = new Result();

        result.setGender(jsonResult.getString(Result.SERIALIZED_GENDER));
        result.setName(parseName(jsonResult.getJSONObject(Result.SERIALIZED_NAME)));
        result.setLocation(parseLocation(jsonResult.getJSONObject(Result.SERIALIZED_LOCATION)));
        result.setEmail(jsonResult.getString(Result.SERIALIZED_EMAIL));
        result.setLogin(parseLogin(jsonResult.getJSONObject(Result.SERIALIZED_LOGIN)));
        result.setDob(jsonResult.getString(Result.SERIALIZED_DOB));
        result.setRegistered(jsonResult.getString(Result.SERIALIZED_REGISTERED));
        result.setPhone(jsonResult.getString(Result.SERIALIZED_PHONE));
        result.setCell(jsonResult.getString(Result.SERIALIZED_CELL));
        result.setId(parseId(jsonResult.getJSONObject(Result.SERIALIZED_ID)));
        result.setPicture(parsePicture(jsonResult.getJSONObject(Result.SERIALIZED_PICTURE)));
        result.setNat(jsonResult.getString(Result.SERIALIZED_NAT));

        return result;
    }

    private static Picture parsePicture(JSONObject jsonPicture) throws JSONException {
        Picture picture = new Picture();

        picture.setLarge(jsonPicture.getString(Picture.SERIALIZED_LARGE));
        picture.setMedium(jsonPicture.getString(Picture.SERIALIZED_MEDIUM));
        picture.setThumbnail(jsonPicture.getString(Picture.SERIALIZED_THUMBNAIL));

        return picture;
    }

    private static Id parseId(JSONObject jsonId) throws JSONException {
        Id id = new Id();

        id.setName(jsonId.getString(Id.SERIALIZED_NAME));
        id.setValue(jsonId.getString(Id.SERIALIZED_VALUE));

        return id;
    }

    private static Login parseLogin(JSONObject jsonLogin) throws JSONException {
        Login login = new Login();

        login.setUsername(jsonLogin.getString(Login.SERIALIZED_USERNAME));
        login.setPassword(jsonLogin.getString(Login.SERIALIZED_PASSWORD));
        login.setSalt(jsonLogin.getString(Login.SERIALIZED_SALT));
        login.setMd5(jsonLogin.getString(Login.SERIALIZED_MD5));
        login.setSha1(jsonLogin.getString(Login.SERIALIZED_SHA1));
        login.setSha256(jsonLogin.getString(Login.SERIALIZED_SHA256));

        return login;
    }

    private static Name parseName(JSONObject jsonName) throws JSONException{
        Name name = new Name();

        name.setTitle(jsonName.getString(Name.SERIALIZED_TITLE));
        name.setFirst(jsonName.getString(Name.SERIALIZED_FIRST));
        name.setLast(jsonName.getString(Name.SERIALIZED_LAST));

        return name;
    }

    private static Location parseLocation(JSONObject jsonLocation) throws JSONException {
        Location location = new Location();

        location.setStreet(jsonLocation.getString(Location.SERIALIZED_STREET));
        location.setCity(jsonLocation.getString(Location.SERIALIZED_CITY));
        location.setState(jsonLocation.getString(Location.SERIALIZED_STATE));
        location.setPostcode(jsonLocation.getString(Location.SERIALIZED_POSTCODE));

        return location;
    }

}
