import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.github.javafaker.Faker;

class AppCardDeliveryTest {

    private Faker faker;
    private String city;
    private String date;
    private String changedDate;
    private String name;
    private String phone;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
        city = faker.address().city();
        date = dateGenerator("ddMMyyyy", false);
        changedDate = dateGenerator("ddMMyyyy", true);
        name = faker.name().lastName().concat(" ").concat(faker.name().firstName());
        phone = "+7".concat(faker.phoneNumber().subscriberNumber(10));
    }

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Успешно!")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldAskForChangingAndChangeMeetingDate() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Успешно!")).waitUntil(Condition.appears, 4000);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(changedDate);
        testData.$("button.button").click();
        $(withText("Необходимо подтверждение")).waitUntil(Condition.appears, 4000);
        testData.$("button.button_hovered").click();
        $(withText("Успешно!")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhileCityFieldIsEmpty() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhileCityFieldIsIncorrectlyFilled() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys("Какой-то-город");
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Доставка в выбранный город недоступна")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhileDateFieldIsEmpty() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Неверно введена дата")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhileDateFieldIsIncorrectlyFilled() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Integer.toString(faker.number().numberBetween(1, 9999999)));
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Неверно введена дата")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhileNameFieldIsEmpty() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=phone]").sendKeys(phone);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhilePhoneFieldIsEmpty() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Поле обязательно для заполнения")).waitUntil(Condition.appears, 4000);
    }

    @Test
    void shouldNotifyForMistakeWhilePhoneFieldIsIncorrectlyFilled() {
        open("http://localhost:9999/");
        SelenideElement testData = $("form");
        testData.$("input[placeholder=Город]").sendKeys(city);
        testData.$("input[placeholder='Дата встречи']").sendKeys(Keys.SHIFT, Keys.HOME, Keys.DELETE);
        testData.$("input[placeholder='Дата встречи']").sendKeys(date);
        testData.$("input[name=name]").sendKeys(name);
        testData.$("input[name=phone]").sendKeys(Long.toString(faker.number().numberBetween(1, 9999999999L)));
        testData.$("label[data-test-id=agreement]").click();
        testData.$("button.button").click();
        $(withText("Телефон указан неверно.")).waitUntil(Condition.appears, 100);
    }

    static String dateGenerator(String datePattern, boolean changeDate){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(datePattern);
        long dateInMillis;
        if(changeDate) {
            dateInMillis = date.getTime() + 583200000L;
        } else {
            dateInMillis = date.getTime() + 259200000L;
        }
        date.setTime(dateInMillis);
        return format.format(date);
    }
}
