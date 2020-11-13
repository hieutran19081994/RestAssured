import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


/**
 * Unit test for simple App.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(glue = {"page.object"},
        features = "features", tags = {"@tagName"}, plugin = {"pretty"})
public class AppTest {
}
