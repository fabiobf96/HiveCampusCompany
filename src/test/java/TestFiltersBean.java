
import it.hivecampuscompany.hivecampus.logic.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.logic.exception.EmptyFieldsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author marina sotiropoulos
 */

class TestFiltersBean {
    @Test
    void testEmptyFieldException() {
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setMaxDistance(5);
        filtersBean.setMaxPrice(500);
        filtersBean.setFeatures(true,true,true,false);

        assertThrows(EmptyFieldsException.class, () -> filtersBean.setUniversity(""));

    }
}
