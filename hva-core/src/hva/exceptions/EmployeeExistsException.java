package hva.exceptions;

import java.io.Serial;

public class EmployeeExistsException extends HotelException {
    
    @Serial
    private static final long serialVersionUID = 202407081733L;

    private final String _key;

    public EmployeeExistsException(String key){
        _key = key;
    }

    public String getkey(){
        return _key;
    }
}