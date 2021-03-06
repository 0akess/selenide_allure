import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import site.stellarburgers.nomoreparties.BaseURL;
import org.apache.http.HttpStatus;
import site.stellarburgers.nomoreparties.data.tests.GetUserData;
import site.stellarburgers.nomoreparties.page.object.AuthorizationPage;
import site.stellarburgers.nomoreparties.respons.model.User;
import site.stellarburgers.nomoreparties.respons.user.DeleteUser;
import site.stellarburgers.nomoreparties.respons.user.PostRegister;

import static com.codeborne.selenide.Selenide.page;

@DisplayName("Сьют на авторизацию пользователя")
public class AuthorizationTest extends BaseTest {

    private static String token;
    private static final GetUserData data = new GetUserData();
    private static final String email = data.getEmailUser();
    private static final String password = data.getPasswordUser(7);
    private static final String name = data.getNameUser();
    private static final AuthorizationPage authorizationPage = page(AuthorizationPage.class);

    @BeforeEach
    @DisplayName("Создаем пользователя для тестов")
    public void startTest() {
        token = new PostRegister().registerUser(new User().builder().email(email).password(password).name(name).build())
                .statusCode(HttpStatus.SC_OK)
                .extract().path("accessToken");
    }

    @AfterEach
    @DisplayName("Удаляем тестового пользователя")
    public void endTests() {
        new DeleteUser().deleteUser(token)
                .statusCode(HttpStatus.SC_ACCEPTED);
    }

    @Test
    @DisplayName("Проверка авторизации со стартом с кнопки Войти в аккаунт на главной")
    public void authorizationButtonEntryToAccount() {
        authorizationPage
                .openPage(BaseURL.MAIN_URL)
                .clickEntryToMainePage()
                .setValueEmail(email)
                .setValuePassword(password)
                .clickLogInToTheSite()
                .checkSuccessfulAuthorization();
    }

    @Test
    @DisplayName("Проверка авторизации со стартом с кнопки Личный кабинет на главной")
    public void authorizationButtonPersonalAccount() {
        authorizationPage
                .openPage(BaseURL.MAIN_URL)
                .clickPersonalAccount()
                .setValueEmail(email)
                .setValuePassword(password)
                .clickLogInToTheSite()
                .checkSuccessfulAuthorization();
    }

    @Test
    @DisplayName("Проверка авторизации со стартом с кнопки Войти на странице регистрации")
    public void authorizationButtonToRegisterPage() {
        authorizationPage
                .openPage(BaseURL.REGISTRATION_URl)
                .clickEntryToRegisterPage()
                .setValueEmail(email)
                .setValuePassword(password)
                .clickLogInToTheSite()
                .checkSuccessfulAuthorization();
    }

    @Test
    @DisplayName("Проверка авторизации со стартом с кнопки Войти на странице восстановить пароль")
    public void authorizationButtonToRecoveryPasswordPage() {
        authorizationPage
                .openPage(BaseURL.FORGOT_PASSWORD_URL)
                .clickEntryToRecoveryPasswordPage()
                .setValueEmail(email)
                .setValuePassword(password)
                .clickLogInToTheSite()
                .checkSuccessfulAuthorization();
    }
}
