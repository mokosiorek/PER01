package kosiorek.michal.jsonconverters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kosiorek.michal.exceptions.AppException;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {

    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    // conversion from object to json
    public String toJson(final T element) {

            if (element == null) {
                throw new NullPointerException("ELEMENT IS NULL");
            }
           return gson.toJson(element);

    }

    public Optional<T> fromJson() {
        try (FileReader fileReader = new FileReader(jsonFilename)) {
            return Optional.of(gson.fromJson(fileReader, type));
        } catch (Exception e) {
            throw new AppException("ELEMENT IS NULL OR ERROR WHILE PARSING");
        }
    }



}
