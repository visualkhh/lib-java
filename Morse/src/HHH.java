public class project {

public static void main ( String [] args ) {

char [] English = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

String [] Morse = { ".-" , "-..." , "-.-." , "-.." , "." , "..-." , "--." , "...." , ".." , ".---" , "-.-" , ".-.." , "--" , "-." , "---" , ".--." , "--.-" ,  ".-." , "..." , "-" , "..-" , "...-" , ".--" , "-..-" , "-.--" , "--.." , "|" };
    String a = Input.getString ( "Please enter \"MC\" if you want to translate Morse Code into English, or \"Eng\" if you want to translate from English into Morse Code" );

    if ( a == "MC" )
    {
        String b = Input.getString ( "Please enter a sentence in Morse Code. Separate each letter/digit with a single space and delimit multiple words with a | ." );

        for ( int x = 0; x < Morse.length; x++ )
        {
            for ( int m = 0; m < b.length (); m++ )
            {
                if ( Morse [ m ] == b.charAt ( m ) )

                System.out.print ( English [ m ] + " " );

            }

        }

    }

    else if ( a == "Eng" )
    {
        String c = Input.getString ( "Please enter a sentence in English, and separate each word with a blank space." );

        c = c.toLowerCase ();

        for ( int x = 0; x < English.length; x++ )
        {
            for ( int y = 0; y < c.length (); y++ )
            {
                if ( English [ x ] == c.charAt ( y ) )

                System.out.print ( Morse [ x ] + " " );


            }

        }


    }

    else 
    {
        System.out.println ( "Invalid Input" );

    }

}