
import java.lang.reflect.AnnotatedElement;

public class AnnotationExample {

 public static void main(String[] args) {
  printAnnotationsForClass(MyClass.class);
 }

 public static void printAnnotationsForClass(Class classObj) {
  System.out.println("Package Notes:");
  printNote(classObj.getPackage());

  System.out.println("\nClass Notes:");
  printNote(classObj);

  System.out.println("\nField Notes:");
  printNotes(classObj.getDeclaredFields());

  System.out.println("\nConstructor Notes:");
  printNotes(classObj.getDeclaredConstructors());

  System.out.println("\nMethod Notes:");
  printNotes(classObj.getDeclaredMethods());

 }

 public static void printNotes(AnnotatedElement[] elems) {
  for (AnnotatedElement elem : elems) {
   printNote(elem);
  }
 }

 public static void printNote(AnnotatedElement elem) {
  if (elem == null || !elem.isAnnotationPresent(Note.class)) {
   return;
  }
  Note annotation = elem.getAnnotation(Note.class);
  String annotationValue = annotation.value();
  Priority annotationPriority = annotation.priority();
  System.out.println(elem.toString() + " - Note: '" + annotationValue
    + "' Priority: " + annotationPriority);
 }

 
 
 
 
 
 @Note(value = "This class isn't finished", priority = Priority.HIGH)
 public class MyClass {

  @Note("This field isn't finished")
  private String fieldA;

  @Note("Constructor isn't finished")
  public MyClass() {

  }

  @Note("methodA isn't finished")
  public void methodA() {

  }
 }

}
