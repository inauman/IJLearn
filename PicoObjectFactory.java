import cucumber.runtime.CucumberException;
import cucumber.runtime.Utils;
import cucumber.runtime.java.picocontainer.PicoFactory;
import org.picocontainer.Characteristics;
import org.picocontainer.MutablePicoContainer;

public class PicoObjectFactory extends PicoFactory {
    private final MutablePicoContainer container;

    public PicoObjectFactory() {
        container = createContainer();
        addClass(MutablePicoContainer.class, container);
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        return container.getComponent(type);
    }

    @Override
    public void stop() {
        container.stop();
    }

    protected MutablePicoContainer createContainer() {
        return super.createContainer().addComponent(new String[0], Characteristics.NO_CACHE);
    }
}
