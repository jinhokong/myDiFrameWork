package di;

import java.util.Arrays;

public class ContainerService {
    public static <T> T getObject(Class<T> classType){
      T instance = createInstance(classType);
      Arrays.stream(classType.getDeclaredFields()).forEach(f->{
        if(f.getAnnotation(Inject.class)!=null){

          Object fieldInstance = createInstance(f.getType());
          f.setAccessible(true);;
          try {
            f.set(instance,fieldInstance);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }
      });
      return createInstance(classType);
    }

  private static <T> T createInstance(Class<T> classType) {
    try {
      return classType.getConstructor(null).newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
