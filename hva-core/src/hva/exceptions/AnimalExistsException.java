package hva.exceptions;

import java.io.Serial;

public class AnimalExistsException extends HotelException{
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final String _key;

    public AnimalExistsException(String key){
        _key = key;
    }

    public String getkey(){
        return _key;
    }
}
