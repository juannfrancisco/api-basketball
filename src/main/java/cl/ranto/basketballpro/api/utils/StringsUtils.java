package cl.ranto.basketballpro.api.utils;


public final class StringsUtils {

    /**
     *
     * @param name
     * @return
     */
    public static String nameID( String name ){
        return name
                .toLowerCase()
                .trim()
                .replaceAll("\\s+", "-");
    }
}
