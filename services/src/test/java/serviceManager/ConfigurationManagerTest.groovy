package serviceManager

import org.junit.Test
import static  org.junit.Assert.*


class ConfigurationManagerTest {
    @Test
    void testGetProperty() {
        String test = "path.page.index";
        assertEquals("/WEB-INF/view/index.jsp",ConfigurationManager.getProperty(test));
    }
}
