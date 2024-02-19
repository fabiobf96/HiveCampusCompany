package it.hivecampuscompany.hivecampus.logic.utility;

public class RoundingFunction {

    private RoundingFunction(){
        //Default constructor
    }
    public static float roundingDouble (float value) {
        // Specifica il valore di soglia per decidere l'arrotondamento
        float d = 0.5F;

        // Estrai la parte decimale
        float decimalPart = (float) (value - Math.floor(value));

        // Decide se arrotondare per eccesso o per difetto
        float result;
        if (decimalPart >= d) {
            result = (float) Math.ceil(value);  // Arrotonda per eccesso
        } else {
            result = (float) Math.floor(value);  // Arrotonda per difetto
        }
        return result;
    }
}
