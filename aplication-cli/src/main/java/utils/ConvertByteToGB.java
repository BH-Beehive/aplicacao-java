package utils;

public class ConvertByteToGB {

    public Long execute(Long bytes){
        Long GB = ((bytes / 1024) / 1024) / 1024;

        return GB;
    }
}
