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
package com.invirgance.divirgance.io;

import com.invirgance.divirgance.jdbc.DivirganceRecord;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Guarantees that a single record will be produced for each record consumed. The
 * record may be modified during this process.
 * 
 * @author jbanes
 */
public interface IdentityTransformer
{
    public DivirganceRecord transform(DivirganceRecord record) throws SQLException;

    public default Iterator<DivirganceRecord> transform(final Iterator<DivirganceRecord> iterator) throws SQLException
    {
        return new CloseableIterator<DivirganceRecord>()  {
            
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }

            @Override
            public DivirganceRecord next()
            {
                try
                {
                    return transform(iterator.next());
                }
                catch(SQLException e) { throw new IllegalArgumentException(e); }
            }

            @Override
            public void close() throws Exception
            {
                if(iterator instanceof CloseableIterator)
                {
                    ((CloseableIterator)iterator).close();
                }
            }
        };
    }

    public default Iterable<DivirganceRecord> transform(final Iterable<DivirganceRecord> iterable) throws SQLException
    {
        return new Iterable<DivirganceRecord>() {
            
            @Override
            public Iterator<DivirganceRecord> iterator()
            {
                try
                {
                    return transform(iterable.iterator());
                }
                catch(SQLException e) { throw new IllegalStateException(e); }
            }
        };
    }
}
