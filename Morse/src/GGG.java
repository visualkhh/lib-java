public class ProjMorse
{
public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", " " };
    String[] dottie = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
            "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
            "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
            "-....", "--...", "---..", "----.", "-----", "|" };

    System.out
            .println("Enter English to convert from English or Morse to convert from Morse:");

    String ans = input.nextLine();

    if (ans.equals("English")) {
        System.out.println("Please enter the text you would like   to convert to Morse Code: ");
        String english = input.nextLine();

        char[] translates = (english.toLowerCase()).toCharArray();
        System.out.println(toMorse(translates, dottie)); //calls on method toMorse

     }else if (ans.equals("Morse")) {
        System.out
                .println("Please enter the text you would like to convert to English (separate words with '|'):");
        String code = input.nextLine();

         String[] translates = (code.split("[|]", 0));
                 System.out.println(toEnglish(translates, alpha));//calls on method toEnglish

    }
else
System.out.println("Invalid input, please try again.");
 }

   public static String toMorse(char [] translates, String [] dottie)
   {            
  String morse = "";
  for (int j = 0; j < translates.length; j++)
  {
    char a = translates[j];
    if(Character.isLetter(a))
    {
       morse = dottie[a + 'a'];
    }
  }
  return morse;/*so I tried running only this(commented other stuff out) and it compiled but although it ran it didnt translate */
}

public static String toEnglish(String [] translates, String  [] alpha)
{
  String s;
  for (int n = 0; n < translates.length; n++)
  {
    String a = translates[n];
            s = java.util.Arrays.asList(alpha).(Character.toChars(a + 'a')[0]);//I'm not sure what to do here..
  }
  return s;
}
}