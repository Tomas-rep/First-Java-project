package hva.exceptions;

import java.io.Serial;

public class VaccineExistsException extends HotelException{
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private String _key;

    public VaccineExistsException(String key){
        _key = key;
    }

    public String getkey(){
        return _key;
    }
}
