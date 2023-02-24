import cucumber.runtime.java.picocontainer.PicoFactory;
import io.cucumber.core.backend.ObjectFactory;
import io.cucumber.core.backend.ObjectFactoryLoader;
import io.cucumber.core.exception.CucumberException;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;
import org.picocontainer.adapters.InstanceAdapter;
import org.picocontainer.injectors.ConstructorInjection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PicoObjectFactory implements ObjectFactory {
    private final MutablePicoContainer container;

    public PicoObjectFactory() {
        container = new DefaultPicoContainer(new ConstructorInjection());
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
        container.stop();
    }

    @Override
    public boolean addClass(Class<?> type) {
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        List<T> instances = getInstances(type);
        if (instances.size() == 0) {
            throw new CucumberException("No PicoContainer bean of type " + type.getName() + " found.");
        } else if (instances.size() > 1) {
            throw new CucumberException("Multiple PicoContainer beans of type " + type.getName() + " found.");
        }
        return instances.get(0);
    }

    @Override
    public <T> List<T> getInstances(Class<T> type) {
        List<T> instances = new ArrayList<>();
        for (Object instance : container.getComponents()) {
            if (type.isInstance(instance)) {
                instances.add(type.cast(instance));
            }
        }
        return Collections.unmodifiableList(instances);
    }

    public void addInstance(Object instance) {
        container.addAdapter(new InstanceAdapter(instance));
    }

    public PicoContainer getContainer() {
        return container;
    }

    public static void main(String[] args) {
        PicoObjectFactory objectFactory = new PicoObjectFactory();
        ObjectFactoryLoader loader = new ObjectFactoryLoader();
        loader.addClass(objectFactory.getClass());
        ObjectFactory factory = loader.loadObjectFactory(PicoObjectFactory.class.getName(), Thread.currentThread().getContextClassLoader());
        factory.start();

        MyStepDefinitions stepDefinitions = factory.getInstance(MyStepDefinitions.class);
        // ...

        factory.stop();
    }
}
