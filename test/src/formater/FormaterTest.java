package formater;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormaterTest {
    public static void main(String[] args)
    {
        // Create a pattern for our MessageFormat object to use.
        String pattern = "I bought {0,number,#} " +
            "apples for {1,number,currency} " +
            "on {2,date,dd-MMM-yyyy}.";

        // Specify which formatters are to be used for each
        // of the placeholders in the pattern above.
        Format[] formats = { new DecimalFormat("#"),
            NumberFormat.getCurrencyInstance(),
            new SimpleDateFormat("MMM/dd/yyyy") };

        // Create values to populate the position in the pattern.
        Object[] values = { new Integer(5), new Double(7.53),
            new Date("04-Jul-2004") };

        // Create a MessageFormat object and apply the pattern
        // to it.
        MessageFormat mFmt = new MessageFormat(pattern);
        mFmt.setFormats(formats);

        // Print out the pattern being used for formatting
        // and the formatted output.
        System.out.println(mFmt.toPattern());
        System.out.println(mFmt.format(pattern, values));
    }
}
