package mate.jdbc.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mate.jdbc.exception.DataProcessingException;

public class Injector {
    private static final char OLD_SYMBOL = '.';
    private static final char NEW_SYMBOL = '/';
    private static final Map<String, Injector> injectors = new HashMap<>();
    private final List<Class<?>> classList = new ArrayList<>();

    private Injector(String mainPackageClass) {
        try {
            classList.addAll(getClasses(mainPackageClass));
        } catch (IOException | ClassNotFoundException e) {
            throw new DataProcessingException("Can't get information about all classes", e);
        }
    }

    public static Injector getInstance(String mainPackageClass) {
        if (injectors.containsKey(mainPackageClass)) {
            return injectors.get(mainPackageClass);
        }
        Injector injector = new Injector(mainPackageClass);
        injectors.put(mainPackageClass, injector);
        return injector;
    }

    public Object getInstance(Class<?> certainInterface) {
        Class<?> classInstance = findClassExtendingInterface(certainInterface);
        return createInstance(classInstance);
    }

    private Class<?> findClassExtendingInterface(Class<?> certainInterface) {
        for (Class<?> classInstance : classList) {
            Class<?>[] interfaces = classInstance.getInterfaces();
            for (Class<?> singleInterface : interfaces) {
                if (singleInterface.equals(certainInterface)
                        && classInstance.isAnnotationPresent(Dao.class)) {
                    return classInstance;
                }
            }
        }
        throw new DataProcessingException("Can't find class which implements "
                + certainInterface.getName()
                + " interface and has valid annotation (Dao or Service)", null);
    }

    private Object createInstance(Class<?> classInstance) {
        Object newInstance;
        try {
            Constructor<?> classConstructor = classInstance.getConstructor();
            newInstance = classConstructor.newInstance();
        } catch (Exception e) {
            throw new DataProcessingException("Can't create object of the class", e);
        }
        return newInstance;
    }

    /**
     * Scans all classes accessible from the context class loader which
     * belong to the given package and subpackages.
     *
     * @param className The base package
     * @return The classes
     * @throws ClassNotFoundException if the class cannot be located
     * @throws IOException            if I/O errors occur
     */
    private static List<Class<?>> getClasses(String className)
            throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new DataProcessingException("Class loader is null", null);
        }
        String path = className.replace(OLD_SYMBOL, NEW_SYMBOL);
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, className));
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param className The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException if the class cannot be located
     */
    private static List<Class<?>> findClasses(File directory, String className)
            throws ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();
        if (!directory.exists()) {
            return classList;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().contains(".")) {
                        throw new DataProcessingException(
                                "File name shouldn't consist point.", null);
                    }
                    classList.addAll(findClasses(file, className + "."
                            + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classList.add(Class.forName(className + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classList;
    }
}
