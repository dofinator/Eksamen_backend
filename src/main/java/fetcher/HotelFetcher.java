package fetcher;

import com.google.gson.Gson;
import dto.FetchHotelDTO;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import utils.HttpUtils;

public class HotelFetcher {

    private static final String HOTELS_URL = "http://exam.cphdat.dk:8000/hotel/all";
    private static final String HOTEL_URL = "http://exam.cphdat.dk:8000/hotel/";

    public static String fetchAllHotels(ExecutorService threadPool, Gson gson) throws InterruptedException, ExecutionException, TimeoutException {

        Callable<FetchHotelDTO[]> hotelTask = new Callable<FetchHotelDTO[]>() {
            @Override
            public FetchHotelDTO[] call() throws Exception {
                String allHotels = HttpUtils.fetchData(HOTELS_URL);
                FetchHotelDTO[] hotelsDTO = gson.fromJson(allHotels, FetchHotelDTO[].class);

                return hotelsDTO;
            }
        };

        Future<FetchHotelDTO[]> futureHotels = threadPool.submit(hotelTask);

        FetchHotelDTO[] result = futureHotels.get(2, TimeUnit.SECONDS);

        String combinedJSON = gson.toJson(result);

        return combinedJSON;
    }

    public static String fetchHotelById(ExecutorService threadPool, Gson gson, String id) throws InterruptedException, ExecutionException, TimeoutException {

        Callable<FetchHotelDTO> hotelTask = new Callable<FetchHotelDTO>() {
            @Override
            public FetchHotelDTO call() throws Exception {
                String hotel = HttpUtils.fetchData(HOTEL_URL + id);
                FetchHotelDTO hotelDTO = gson.fromJson(hotel, FetchHotelDTO.class);

                return hotelDTO;
            }
        };

        Future<FetchHotelDTO> futureHotel = threadPool.submit(hotelTask);

        FetchHotelDTO result = futureHotel.get(2, TimeUnit.SECONDS);
        String combinedJSON = gson.toJson(result);

        return combinedJSON;
    }

}
