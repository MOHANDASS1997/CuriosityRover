package com.curiosityrover.model;


import org.json.JSONException;
import org.json.JSONObject;

public class CustomImageCard  {
    private String image_url, url;
    private String image_id, sol, camera, earth_date;


    public CustomImageCard(JSONObject object) {

        try {
            this.image_id = Integer.toString(object.getInt("id"));
            url= object.getString("img_src");
            if(url.charAt(4) == 's'){
                this.image_url = url;
            } else {
                this.image_url = url.substring(0, 4) + "s" + url.substring(4);
            }
            this.sol = Integer.toString(object.getInt("sol"));
            this.camera = object.getJSONObject("camera").getString("name");
            this.earth_date = object.getString("earth_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public CustomImageCard(String url, String id, String sol, String camera, String date){
        this.image_url = url;
        this.image_id = id;
        this.sol = sol;
        this.camera = camera;
        this.earth_date = date;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getImage_id() {
        return image_id;
    }

    public String getSol() { return sol; }

    public String getCamera() { return camera;  }

    public String getEarth_date() { return earth_date;  }
}
