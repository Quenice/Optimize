package com.quenice.optimize.netframe.retrofit;

import com.quenice.optimize.common.Req;
import com.quenice.optimize.common.Resp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qiubb on 2016/10/27.
 */
public interface HttpService {
	@POST("json.do")
	Call<Resp<List<Model>>> post(@Body Req<Model> req);

	@Headers("Need-Token:false")
	@POST("json.do")
	Observable<Resp<List<Model>>> postRxJava(@Body Model model);

	@POST("json.do")
	Observable<Resp<List<Model>>> postRxJava2(@Body String model);

	@FormUrlEncoded
	@POST("http://api.t.sina.com.cn/short_url/shorten.json?source=1268393953")
	Observable<List<Object>> testUrl(@Field("url_long") String ... urls);
}
