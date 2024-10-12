
import com.invirgance.divirgance.jdbc.DivirganceDriver;
import java.sql.Connection;
import java.sql.ResultSet;

/*
 * Copyright 2024 INVIRGANCE LLC

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the “Software”), to deal 
in the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */

/**
 *
 * @author jbanes
 */
public class Test
{
    public static void main(String[] args) throws Exception
    {
        DivirganceDriver driver = new DivirganceDriver();
        
        try(Connection connection = driver.connect("jdbc:divirgance://localhost", null))
        {
            try(ResultSet set = connection.getMetaData().getCatalogs())
            {
                System.out.println();
                System.out.println("==Databases======");
                System.out.println();

                while(set.next())
                {
                    System.out.println(set.getString(1));
                }
            }

            try(ResultSet set = connection.getMetaData().getTables("TestDatabase", null, null, null))
            {
                System.out.println();
                System.out.println("==Tables=========");
                System.out.println();

                while(set.next())
                {
                    System.out.println(set.getString(1));
                }
            }
        }
    }
}
