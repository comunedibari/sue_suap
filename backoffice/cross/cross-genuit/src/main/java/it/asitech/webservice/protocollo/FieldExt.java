/**
 * FieldExt.java
 *
 * Wrapper oggetto Axis Field
 * espone metodi facility per l'accesso a Field
 * Alex/ASi 18/8/2010
 */

package it.asitech.webservice.protocollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FieldExt  implements java.io.Serializable {
    private static final long serialVersionUID = -3435291968852125904L;

    private final Field fi ;

    public FieldExt( Field fi ) {
        this.fi = fi;
    }


    //WARNING: non chiamarlo isNull perch si incasina tutto e non so perch
    // le funzioni boolean cha si chaimano xxNull incasinano axis
    public boolean isNull() {
        return this.fi.getValue().length==0;
    }

    private String qt(String vl){
        return "\""+vl+"\"";
    }
    private String qt(Object vl){
        if( vl instanceof String )
            return qt((String)vl);
        return vl+"";
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append( "{" )
          //.append( fi.getClass().getName() )
          .append( "name=" ).append( qt( this.fi.getName() ) )
          .append( " _value=[" );

        int i=0;
        for( Object o : this.fi.getValue() ) {
            if( i++ > 0 ) {
                sb.append( ", " );
            }
            sb.append( qt(o) );
        }
        sb.append( "]}" );

        return sb.toString();
    }

    public void setValue( Object value )
    {
        this.fi.setValue( new Object[] { value } );
    }

    public java.lang.Object[] getValue() {
        return this.fi.getValue();
    }

    public java.lang.String getName() {
        return this.fi.getName();
    }

    public Field getField() {
        return this.fi;
    }

    public static Field create( String name, Object value )
    {
        return new Field( name, value == null ? new Object[0] : new Object[] { value } );
    }

    public static Field create( String name, PriObject[] values )
    {
        return new Field( name, values == null ? new Object[0] : values );
    }

    public static FieldExt createExt( String name, PriObject[] values )
    {
        return new FieldExt( new Field( name, values == null ? new Object[0] : values ) );
    }


    public void addValues( Object ... values )
    {
        addValues( this.fi, values );
    }


    public static void addValues( Field field , Object ... values )
    {
        List<Object> list = new ArrayList<Object>();
        Collections.addAll( list, field.getValue());

        for( Object v : values ) {
            if( v != null ){
                list.add( v );
            }
        }
        field.setValue( list.toArray( new Object[list.size()] ) );
    }

}
