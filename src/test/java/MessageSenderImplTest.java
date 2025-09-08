

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

class MessageSenderImplTest {
    private MessageSender messageSender;
    GeoService geoService;
    LocalizationService localizationService;
    Map<String, String> headers = new HashMap<>();
    String expectedText;

    @BeforeEach
    void createStubsForExternalServices() {
        headers = new HashMap<>();
        geoService = Mockito.mock(GeoServiceImpl.class);
        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        messageSender = new MessageSenderImpl(geoService, localizationService);
    }

    @Test
    @DisplayName(" отправляется русский текст, если ip  российский")
    void sendShouldReturnRussianTextForRussianIP() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.RUSSIAN_IP);
        expectedText = "Добро пожаловать";

        Mockito.when(geoService.byIp(GeoServiceImpl.RUSSIAN_IP))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        Assertions.assertEquals(expectedText, messageSender.send(headers));
    }

    @Test
    @DisplayName("отправляется английский текст, если ip не российский ")
    void sendShouldReturnEnglishTextForNotRussianIP() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.USA_IP);
        expectedText = "Welcome";

        Mockito.when(geoService.byIp(GeoServiceImpl.USA_IP))
                .thenReturn(new Location("Boston", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");

        Assertions.assertEquals(expectedText, messageSender.send(headers));
    }


    }
