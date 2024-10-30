package hva.exceptions;

import java.io.Serial;

public class NoAuthorizedVetException extends HotelException {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final String _key1;
    private final String _key2;

    public NoAuthorizedVetException(String key1, String key2){
        _key1 = key1;
        _key2 = key2;
    }

    public String getkey1(){
        return _key1;
    }
    public String getkey2(){
        return _key2;
    }
}
