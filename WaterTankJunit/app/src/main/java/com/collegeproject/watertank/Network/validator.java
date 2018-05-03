
package com.collegeproject.watertank.Network;

public class validator {

    public static boolean isValidEmail(String emailstr)
    {
        if(emailstr.contains("@")&&emailstr.contains(".")&& !emailstr.startsWith("@")&&!emailstr.endsWith(".")&&emailstr.indexOf("@")<emailstr.lastIndexOf("."))
            return true;
        return false;

    }

    public static boolean isValidmobile(String mobstr)
    {
        if(mobstr.startsWith("05")&&mobstr.length()==10)
            return true;
        return false;

    }
}
