package app.me.hungrykiwi.http.zomato;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * Created by user on 10/18/2016.
 */

public class ZomatoClient extends AsyncHttpClient{
    private RequestParams params;
    public ZomatoClient() {
        this.params = new RequestParams();
        this.addHeader("user_key", "b37e2a5d0982a1db86bb974caed7e1cc");
    }

    public void put(String key, Object value) {
        this.params.put(key, value);
    }

    @Override
    public String toString() {
        String info = null;
        if(this.params !=null) {
            info = this.params.toString();
        }
        return info;
    }

}
