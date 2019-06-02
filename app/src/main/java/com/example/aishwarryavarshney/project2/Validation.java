package com.example.aishwarryavarshney.project2;

public class Validation {
    public boolean myisEmpty(String field)
    {
       return field.isEmpty();
    }
public boolean myisEmail(String field)
{
    int l,pos1,pos2;
    l=field.length();
    pos1=field.lastIndexOf('.');
    pos2=field.lastIndexOf('@');
    if(l>7 && pos1-pos2>2 && pos2!=-1 && pos2>=2 && l-pos1>2)
    {
        return false;
    }
    return true;

}
    public boolean isValid(String s)
    {
        int l=s.length();
        for(int i=0;i<l;i++)
        {
            if(Character.isLetterOrDigit(s.charAt(i)) || Character.isSpaceChar(s.charAt(i)) || s.charAt(i)=='/' || s.charAt(i)=='-' )
                return true;
        }
        return false;
    }


}
