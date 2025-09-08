import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocatizationServiceImplTest {
    @Test
    void localeShouldReturnCorrectMessage() {
        var localizationService = new LocalizationServiceImpl();
        var expectedText = "Добро пожаловать";
        Assertions.assertEquals(expectedText, localizationService.locale(Country.RUSSIA));
    }
}
