package api.bot;

import api.model.WeekWeatherModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {


    public static String get5DaysWeather(String cityName, WeekWeatherModel weekWeatherModel) throws IOException {

        //93fd8e0a9c66f31c607cacb02d504f53
        //double latitude = Double.parseDouble(lat);
        ///double longitude = Double.parseDouble(lon);
        URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?"+cityName+"&units=metric&exclude=current,minutely,hourly,alerts&appid=93fd8e0a9c66f31c607cacb02d504f53");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";

        //https://api.openweathermap.org/data/2.5/onecall?lat=51.51&lon=-0.13&exclude=current,minutely,hourly,alerts&appid=93fd8e0a9c66f31c607cacb02d504f53

        while (scanner.hasNext()) {
            result += scanner.nextLine();
        }

        //full json Object
        JSONObject jsonObject = new JSONObject(result);
        weekWeatherModel.setTimeZone((String) jsonObject.get("timezone"));

        //"daily" in json
        JSONArray dailyArray = jsonObject.getJSONArray("daily");
        double currentMax = 0, avgTemp = 0;
        for (int i = 0; i < 5; i++) {

            JSONObject tempJson = dailyArray.getJSONObject(i);
            JSONObject temp = tempJson.getJSONObject("temp");


            double morn = temp.getDouble("morn");
            if (morn > currentMax) currentMax = morn;
            System.out.println(morn);
            avgTemp = avgTemp + morn;

            weekWeatherModel.setMornTemp(avgTemp / 5);
            weekWeatherModel.setMaxTemp(currentMax);
        }

        return "timeZone: " + weekWeatherModel.getTimeZone() + "\n" +
                "Morning average Temperature: " + weekWeatherModel.getMornTemp() + "C\n" +
                "Maximum Morning Temperature: " + weekWeatherModel.getMaxTemp() + "C\n";
    }
}
