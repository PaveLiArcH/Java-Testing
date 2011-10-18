/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arraytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author Pavel
 */
public class ArrayTest
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        int []_testArray={1, 1001, -4224, 42, 100500, 1101};
        BufferedReader _bur=new BufferedReader(new InputStreamReader(System.in));
        String _str;
        try
        {
            _str=_bur.readLine();
        } catch (IOException e)
        {
            System.err.println("Error: "+e);
        }
        
        Arrays.sort(_testArray);
        for(int i = 0; i < _testArray.length; i++)
        {
            System.out.println(i + ". " + _testArray[i]);
        }
        int _searched=1001;
        System.out.println("Индекс искомого значения "+_searched+" равен "+cm_TestSearch(_testArray, _searched));
    }
    
    public static int cm_TestSearch(int []a_array, int a_searched)
    {
        int l=0, h=a_array.length;
        while (l<=h)
        {
            int m = (l+h) >> 1;
            if (a_array[m]==a_searched) return m;
            if (a_array[m]<a_searched) h=m-1;
            if (a_array[m]>a_searched) l=m+1;
        }
        return -1;
    }
}
